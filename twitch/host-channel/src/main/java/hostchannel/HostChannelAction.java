package hostchannel;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class HostChannelAction extends NormalAction
{

    private final String channelKey = "channel_hc";
    private final String channelToHostKey = "channel_to_host_hc";

    private Twirk twirk;

    public HostChannelAction()
    {
        setName("Host Channel");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws Exception
    {
        Property channel = new Property(channelKey, Type.STRING);
        channel.setDisplayName("Channel");
        channel.setDefaultValueStr("channel");
        channel.setCanBeBlank(false);

        Property channelToHost = new Property(channelToHostKey, Type.STRING);
        channelToHost.setDisplayName("Channel to host");
        channelToHost.setDefaultValueStr("channel_to_host");
        channelToHost.setCanBeBlank(false);

        addClientProperties(channel, channelToHost);
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

        final String channel = getClientProperties().getSingleProperty(channelKey).getStringValue();
        final String channelToHost = getClientProperties().getSingleProperty(channelToHostKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage(String.format("/host %s", channelToHost));
        } catch (Exception ex)
        {
            throw new StreamPiException(
                    "Failed to host channel",
                    String.format("Could not host channel '%s', please try again.", channelToHost));
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
