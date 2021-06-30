package hostchannel;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
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
        setHelpLink(TwitchChatCredentials.HELP_LINK);
    }

    @Override
    public void initProperties() throws MinorException
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
    public void onActionClicked() throws MinorException
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
            throw new MinorException(
                    "Failed to host channel",
                    String.format("Could not host channel '%s', please try again.", channelToHost));
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
                throw new MinorException("Twitch connection error - Please try again.");
            }
        }
    }
}
