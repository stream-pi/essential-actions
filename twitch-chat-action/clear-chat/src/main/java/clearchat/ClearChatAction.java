package clearchat;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class ClearChatAction extends NormalAction
{

    private final String channelNameKey = "channel_name_cc";

    private Twirk twirk;

    @Override
    public void initProperties() throws MinorException
    {
        setName("Clear Chat");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink(TwitchChatCredentials.HELP_LINK);
    }

    @Override
    public void initAction() throws MinorException
    {
        Property channelName = new Property(channelNameKey, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        addClientProperties(channelName);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage("/clear");
        } catch (Exception ex)
        {
            throw new MinorException(
                   String.format("Could not clear chat for '%s' channel, please try again.", channel)
            );
        }
    }

    @Override
    public void onShutDown() throws MinorException
    {
        if (twirk != null)
        {
            try
            {
                twirk.disconnect();
            } catch (Exception ex)
            {
                throw new MinorException("Twitch Connection error - Please try again.");
            }
        }
    }
}
