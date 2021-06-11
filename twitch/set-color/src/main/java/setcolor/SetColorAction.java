package setcolor;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.ListProperty;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class SetColorAction extends NormalAction
{

    private final String channelNameKey = "channel_name_sc";
    private final String usernameColorKey = "username_color_sc";

    private Twirk twirk;

    @Override
    public void initProperties()
    {
        setName("Set Color");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initAction() throws MinorException
    {
        Property channelName = new Property(channelNameKey, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        ListProperty usernameColor = new ListProperty(usernameColorKey);
        usernameColor.setDisplayName("Color");


        addClientProperties(channelName, usernameColor);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();
        final String color = getClientProperties().getSingleProperty(usernameColorKey).getStringValue();

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
