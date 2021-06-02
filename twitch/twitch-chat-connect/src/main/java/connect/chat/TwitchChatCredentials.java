package connect.chat;

import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.exception.StreamPiException;

public final class TwitchChatCredentials
{

    private static ChatCredentials credentials;

    public static ChatCredentials getCredentials()
    {
        return credentials;
    }

    public static void setCredentials(ChatCredentials chatCredentials)
    {
        credentials = chatCredentials;
    }

    public static class ChatCredentials
    {
        String nickname;
        String oauthToken;

        public void ensureCredentialsInitialized() throws MinorException
        {
            final String twitchNickname = nickname;
            boolean isNicknameInitialized = twitchNickname != null && !twitchNickname.isEmpty();

            final String twitchChatOauthToken = oauthToken;
            boolean isTokenInitialized = twitchChatOauthToken != null && !twitchChatOauthToken.isEmpty();

            if (!isNicknameInitialized && !isTokenInitialized) {
                throw new MinorException(
                        "Twitch Chat config uninitialized\n"+
                        "Please check that the Twitch Chat plugin configuration is correct.");
            }
        }

        public String getNickname()
        {
            return nickname;
        }

        public ChatCredentials setNickname(String nickname)
        {
            this.nickname = nickname;
            return this;
        }

        public String getOauthToken()
        {
            return oauthToken;
        }

        public ChatCredentials setOauthToken(String oauthToken)
        {
            this.oauthToken = oauthToken;
            return this;
        }
    }
}
