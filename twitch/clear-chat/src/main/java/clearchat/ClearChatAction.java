package clearchat;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

import java.util.UUID;

public class ClearChatAction extends NormalAction
{

    private static final String CHANNEL_NAME_KEY = UUID.randomUUID().toString();

    private Twirk twirk;

    @Override
    public void initProperties() throws Exception
    {
        setName("Clear Chat");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initAction() throws Exception
    {
        Property channelName = new Property(CHANNEL_NAME_KEY, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        addClientProperties(channelName);
    }

    @Override
    public void onActionClicked() throws Exception
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(CHANNEL_NAME_KEY).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage("/clear");
        } catch (Exception ex)
        {
            throw new StreamPiException(
                    "Failed to clear channel chat",
                    String.format("Could not clear chat for '%s' channel, please try again.", channel)
            );
        }
    }

    @Override
    public void onShutDown() throws Exception
    {
        twirk.close();
    }
}
