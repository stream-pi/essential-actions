package unhost;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class UnhostAction extends NormalAction
{

    private final String channelNameKey = "channel_name_uh";

    private Twirk twirk;

    public UnhostAction()
    {
        setName("Unhost");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws Exception
    {
        Property channel = new Property(channelNameKey, Type.STRING);
        channel.setDisplayName("Channel");
        channel.setDefaultValueStr("channel_name");
        channel.setCanBeBlank(false);

        addClientProperties(channel);
    }

    @Override
    public void initAction() throws Exception
    {

    }

    @Override
    public void onActionClicked() throws Exception
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage("/unhost");
        } catch (Exception ex)
        {
            throw new StreamPiException(
                    "Failed to cancel channel hosting",
                    "Could not cancel channel hosting, please try again.");
        }
    }

    @Override
    public void onShutDown() throws Exception
    {
        if (twirk != null) {
            try
            {
                twirk.disconnect();
            } catch (Exception ex) {
                throw new StreamPiException("Twitch connection error", "Please try again.");
            }
        }
    }
}
