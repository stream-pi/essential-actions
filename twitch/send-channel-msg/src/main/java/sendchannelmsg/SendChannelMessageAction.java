package sendchannelmsg;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class SendChannelMessageAction extends NormalAction
{

    private static final String TWITCH_CHANNEL_NAME_KEY = "twitch_channel_name";
    private static final String TWITCH_CHANNEL_MSG_KEY = "twitch_channel_msg";

    private Twirk twirk;

    public SendChannelMessageAction()
    {
        setName("Send Channel Message");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions");
    }

    @Override
    public void initProperties() throws Exception
    {
        Property channelName = new Property(TWITCH_CHANNEL_NAME_KEY, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        Property channelMessage = new Property(TWITCH_CHANNEL_MSG_KEY, Type.STRING);
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
        TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        if (!isChatCredentialsInitialized(credentials))
        {
            throw new StreamPiException("Twitch Chat uninitialized.","Please check that the Twitch Chat plugin configuration is correct.");
        }

        final String channel = getClientProperties().getSingleProperty(TWITCH_CHANNEL_NAME_KEY).getStringValue();
        final String message = getClientProperties().getSingleProperty(TWITCH_CHANNEL_MSG_KEY).getStringValue();

        try
        {
            twirk = new TwirkBuilder(
                    getClientProperties().getSingleProperty(TWITCH_CHANNEL_NAME_KEY).getStringValue(),
                    credentials.getNickname(),
                    credentials.getOauthToken())
                    .build();
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

    private boolean isChatCredentialsInitialized(TwitchChatCredentials.ChatCredentials credentials)
    {
        if (credentials == null)
        {
            return false;
        }

        final String twitchNickname = credentials.getNickname();
        boolean isNicknameInitialized = twitchNickname != null &&
                !twitchNickname.isEmpty() &&
                !twitchNickname.equalsIgnoreCase("twitch_nickname");

        final String twitchChatOauthToken = credentials.getOauthToken();
        boolean isTokenInitialized = twitchChatOauthToken != null &&
                !twitchChatOauthToken.isEmpty() &&
                !twitchChatOauthToken.equalsIgnoreCase("twitch_oauth_token");

        return isNicknameInitialized && isTokenInitialized;
    }

    @Override
    public void onShutDown() throws Exception
    {
        twirk.close();
    }
}


