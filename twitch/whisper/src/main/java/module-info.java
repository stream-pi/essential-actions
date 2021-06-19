module com.stream_pi.twitch.whisperaction {
    requires com.stream_pi.util;
    requires com.stream_pi.action_api;

    requires com.stream_pi.twitchchatconnectaction;
    requires twitch.api.java;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with whisper.WhisperAction;
}
