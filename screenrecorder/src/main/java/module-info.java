module com.stream_pi.screenrecorder
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;
    requires XR3Capture;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.screenrecorder.ScreenRecorder;
}