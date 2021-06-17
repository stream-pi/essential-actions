module com.stream_pi.twitchchatconnectaction {

    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires twitch.api.java;

    exports connect.chat;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with connect.TwitchChatConnectAction;
}
