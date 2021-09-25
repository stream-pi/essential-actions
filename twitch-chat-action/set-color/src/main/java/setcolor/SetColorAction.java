package setcolor;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.ListProperty;
import com.stream_pi.action_api.actionproperty.property.ListValue;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetColorAction extends NormalAction
{

    private final String channelNameKey = "channel_name_sc";
    private final String usernameColorKey = "username_color_sc";

    private Twirk twirk;

    public SetColorAction()
    {
        setName("Set Color");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink(TwitchChatCredentials.HELP_LINK);

        colors = new ArrayList<>();
        colors.addAll(
                Arrays.asList(
                        new ListValue("Blue"),
                        new ListValue("Coral"),
                        new ListValue("DodgerBlue", "Dodger Blue"),
                        new ListValue("SpringGreen", "Spring Green"),
                        new ListValue("YellowGreen", "Yellow Green"),
                        new ListValue("Green"),
                        new ListValue("OrangeRed", "Orange Red"),
                        new ListValue("Red"),
                        new ListValue("GoldenRod", "Golden Rod"),
                        new ListValue("HotPink", "Hot Pink"),
                        new ListValue("CadetBlue", "Cadet Blue"),
                        new ListValue("SeaGreen", "Sea Green"),
                        new ListValue("Chocolate"),
                        new ListValue("BlueViolet", "Blue Violet"),
                        new ListValue("Firebrick")  
                )
        );
    }

    private final ArrayList<ListValue> colors;

    @Override
    public void initAction() throws MinorException
    {
        Property channelName = new Property(channelNameKey, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        ListProperty usernameColor = new ListProperty(usernameColorKey);
        usernameColor.setDisplayName("Color");
        usernameColor.setListValue(colors);

        addClientProperties(channelName, usernameColor);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();
        final String color = colors.get(getClientProperties().getSingleProperty(usernameColorKey).getSelectedIndex()).getName().toString();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage(String.format("/color %s", color));
        } catch (Exception ex)
        {
            throw new MinorException(
                    "Failed to change username color",
                    String.format("Could not change username color to '%s' for '%s' channel, please try again.",
                            color, channel)
            );
        }
    }

    @Override
    public void onShutDown() throws MinorException
    {
        if (twirk != null) {
            try
            {
                twirk.disconnect();
            } catch (Exception ex) {
                throw new MinorException("Twitch connection error", "Please try again.");
            }
        }
    }
}
