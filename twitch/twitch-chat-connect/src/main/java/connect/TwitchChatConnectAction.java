package connect;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;
import javafx.scene.control.Button;

public class TwitchChatConnectAction extends NormalAction
{

    private static final String TWITCH_ACCESS_TOKEN_KEY = "twitch_access_token";
    private static final String TWITCH_NICKNAME_KEY = "twitch_nickname";

    private final Button saveCredentialsBtn;

    public TwitchChatConnectAction()
    {
        setName("Twitch Chat Plugin");
        setCategory("Twitch Chat");
        setVisibilityInPluginsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/Stream-Pi/essentialactions");

        saveCredentialsBtn = new Button("Save Twitch chat credentials");
        setButtonBar(saveCredentialsBtn);
    }

    @Override
    public void initProperties() throws Exception
    {
        Property twitchNicknameProp = new Property(TWITCH_NICKNAME_KEY, Type.STRING);
        twitchNicknameProp.setDisplayName("Twitch Username");
        twitchNicknameProp.setDefaultValueStr("twitch_nickname");
        twitchNicknameProp.setCanBeBlank(false);

        Property twitchAccessTokenProp = new Property(TWITCH_ACCESS_TOKEN_KEY, Type.STRING);
        twitchAccessTokenProp.setDisplayName("Access Token");
        twitchAccessTokenProp.setDefaultValueStr("twitch_oauth_token");
        twitchAccessTokenProp.setCanBeBlank(false);

        addServerProperties(twitchNicknameProp, twitchAccessTokenProp);
    }

    @Override
    public void initAction() throws Exception
    {
        saveCredentialsBtn.setOnAction(action ->
        {
            try
            {
                persistCredentials();

                new StreamPiAlert(
                        "Twitch chat credentials saved",
                        "Chat credentials been saved, you can now start using Twitch chat integration actions.",
                        StreamPiAlertType.INFORMATION)
                        .show();
            } catch (Exception e)
            {
                new StreamPiAlert(
                        "Failed to save chat credentials",
                        "An error has occurred while saving chat credentials, please try again.",
                        StreamPiAlertType.WARNING)
                        .show();
            }
        });

        persistCredentials();
    }

    private void persistCredentials() throws MinorException
    {
        final String token = getServerProperties().getSingleProperty(TWITCH_ACCESS_TOKEN_KEY).getStringValue();
        final String nickname = getServerProperties().getSingleProperty(TWITCH_NICKNAME_KEY).getStringValue();
        TwitchChatCredentials.setCredentials(
                new TwitchChatCredentials.ChatCredentials()
                        .setOauthToken(token)
                        .setNickname(nickname));
    }

    @Override
    public void onActionClicked() throws Exception
    {
    }

    @Override
    public void onShutDown() throws Exception
    {
    }
}
