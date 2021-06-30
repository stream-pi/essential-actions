package connect;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;
import javafx.scene.control.Button;

public class TwitchChatConnectAction extends NormalAction
{

    private static final String ACCESS_TOKEN_KEY = "twitch_chat_access_token";
    private static final String NICKNAME_KEY = "twitch_chat_nickname";

    private final Button clearCredentialsBtn;

    public TwitchChatConnectAction()
    {
        setName("Twitch Chat Plugin");
        setCategory("Twitch Chat");
        setVisibilityInPluginsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink(TwitchChatCredentials.HELP_LINK);

        clearCredentialsBtn = new Button("Clear Twitch chat credentials");
        onClearCredentials();
        setServerSettingsButtonBar(clearCredentialsBtn);
    }

    @Override
    public void onActionClicked() throws MinorException
    {

    }

    @Override
    public void initProperties() throws MinorException
    {
        Property twitchNicknameProp = new Property(NICKNAME_KEY, Type.STRING);
        twitchNicknameProp.setDisplayName("Twitch Username");

        Property twitchAccessTokenProp = new Property(ACCESS_TOKEN_KEY, Type.STRING);
        twitchAccessTokenProp.setControlType(ControlType.TEXT_FIELD_MASKED);
        twitchAccessTokenProp.setDisplayName("Access Token");

        addServerProperties(twitchNicknameProp, twitchAccessTokenProp);
    }

    @Override
    public void initAction() throws MinorException
    {
        clearCredentialsBtn.setDisable(isEmptyCredentials());

        TwitchChatCredentials.setCredentials(
                new TwitchChatCredentials.ChatCredentials()
                        .setOauthToken(getServerProperties().getSingleProperty(ACCESS_TOKEN_KEY).getStringValue())
                        .setNickname(getServerProperties().getSingleProperty(NICKNAME_KEY).getStringValue()));
    }

    private boolean isEmptyCredentials() throws MinorException {
        final String twitchNickname = getServerProperties().getSingleProperty(NICKNAME_KEY).getStringValue();
        final String twitchChatOauthToken = getServerProperties().getSingleProperty(ACCESS_TOKEN_KEY).getStringValue();
        return (twitchNickname == null || twitchNickname.isEmpty()) &&
                (twitchChatOauthToken == null || twitchChatOauthToken.isEmpty());
    }

    private void onClearCredentials()
    {
        clearCredentialsBtn.setOnAction(action ->
        {
            try
            {
                getServerProperties().getSingleProperty(ACCESS_TOKEN_KEY).setStringValue("");
                getServerProperties().getSingleProperty(NICKNAME_KEY).setStringValue("");
                saveServerProperties();
                new StreamPiAlert(
                        "Twitch chat credentials cleared",
                        "To revoke token access, disconnect \"Twitch Chat OAuth Token Generator\" from your Twitch settings (https://www.twitch.tv/settings/connections).",
                        StreamPiAlertType.INFORMATION)
                        .show();
            } catch (Exception e)
            {
                new StreamPiAlert(
                        "Failed to save chat credentials",
                        "An error has occurred while clearing chat credentials, please try again.",
                        StreamPiAlertType.WARNING)
                        .show();
            }
        });
    }
}
