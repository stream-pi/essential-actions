package connect.chat;

public final class TwitchChatCredentials
{
    public static final String DEFAULT_TWITCH_NICKNAME = "twitch_username";
    public static final String DEFAULT_TWITCH_TOKEN = "twitch_token";

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
