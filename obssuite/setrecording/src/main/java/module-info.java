module com.stream_pi.obssuite.setrecordingaction
{
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires obs.websocket.java;
    requires com.stream_pi.obssuite.motheraction;

    provides com.stream_pi.action_api.normalaction.NormalAction with setrecording.SetRecording;
}
