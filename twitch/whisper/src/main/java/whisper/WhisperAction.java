package whisper;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class WhisperAction extends NormalAction
{

    private final String usernameKey = "username_wa";
    private final String messageKey = "message_wa";

    private Twirk twirk;

    public WhisperAction()
    {
        setName("Whisper");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws Exception
    {
        Property usernameProp = new Property(usernameKey, Type.STRING);
        usernameProp.setDisplayName("Twitch Username");
        usernameProp.setDefaultValueStr("username");
        usernameProp.setCanBeBlank(false);

        Property messageProp = new Property(messageKey, Type.STRING);
        messageProp.setDisplayName("Message");
        messageProp.setDefaultValueStr("message");
        messageProp.setCanBeBlank(false);

        addClientProperties(usernameProp, messageProp);
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

        final String username = getClientProperties().getSingleProperty(usernameKey).getStringValue();
        final String message = getClientProperties().getSingleProperty(messageKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(username, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.whisper(username, message);
        } catch (Exception ex)
        {
            throw new StreamPiException(
                    "Failed to send message to user",
                    String.format("Could not send message '%s' to user '%s', please try again.",
                            username, message)
            );
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
