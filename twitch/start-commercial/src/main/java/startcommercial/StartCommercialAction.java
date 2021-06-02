package startcommercial;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

import java.util.List;

public class StartCommercialAction extends NormalAction
{

    private final String channelNameKey = "channel_name_sca";
    private final String durationKey = "duration_sca";

    private Twirk twirk;

    public StartCommercialAction()
    {
        setName("Start commercial");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property channelName = new Property(channelNameKey, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        Property duration = new Property(durationKey, Type.LIST);
        duration.setDisplayName("Duration");
        duration.setListValue(List.of(
                String.valueOf(30),
                String.valueOf(60),
                String.valueOf(90),
                String.valueOf(120),
                String.valueOf(150),
                String.valueOf(180)));

        addClientProperties(channelName, duration);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();
        final String duration = getClientProperties().getSingleProperty(durationKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage(String.format("/commercial %s", duration));
        } catch (Exception ex)
        {
            throw new MinorException(
                    "Failed to start commercial", "Could not start commercial, please try again.");
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
