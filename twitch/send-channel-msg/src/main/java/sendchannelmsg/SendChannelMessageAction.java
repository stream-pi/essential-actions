package sendchannelmsg;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

import java.util.UUID;

public class SendChannelMessageAction extends NormalAction
{

    private static final String CHANNEL_NAME_KEY = UUID.randomUUID().toString();
    private static final String CHANNEL_MSG_KEY = UUID.randomUUID().toString();

    private Twirk twirk;

    public SendChannelMessageAction()
    {
        setName("Send Channel Message");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws Exception
    {
        Property channelName = new Property(CHANNEL_NAME_KEY, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        Property channelMessage = new Property(CHANNEL_MSG_KEY, Type.STRING);
        channelMessage.setDisplayName("Message");
        channelMessage.setDefaultValueStr("channel_msg");
        channelMessage.setCanBeBlank(false);

        addClientProperties(channelName, channelMessage);
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

        final String channel = getClientProperties().getSingleProperty(CHANNEL_NAME_KEY).getStringValue();
        final String message = getClientProperties().getSingleProperty(CHANNEL_MSG_KEY).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage(message);
        } catch (Exception ex)
        {
            throw new StreamPiException(
                    "Failed to send channel message",
                    String.format("Could not send message '%s' to '%s' channel, please try again.",
                            channel, message)
            );
        }
    }

    @Override
    public void onShutDown() throws Exception
    {
        twirk.close();
    }
}
