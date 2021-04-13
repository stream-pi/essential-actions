module com.stream_pi.twitch.sendchannelmsgaction {
    requires com.stream_pi.util;
    requires com.stream_pi.action_api;

    requires com.stream_pi.twitchchatconnectaction;
    requires Java.Twirk;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with sendchannelmsg.SendChannelMessageAction;
}
