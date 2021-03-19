module com.stream_pi.democustomtoggleaction
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;
    provides com.stream_pi.action_api.normalaction.ExternalPlugin with com.stream_pi.democustomtoggleaction.DemoCustomToggleAction;
}
