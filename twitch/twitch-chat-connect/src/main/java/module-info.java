import connect.TwitchChatConnectAction;

module com.stream_pi.twitchconnectaction {

    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires Java.Twirk;

    exports connect.chat;

    provides com.stream_pi.action_api.normalaction.NormalAction with TwitchChatConnectAction;
}