module com.stream_pi.sleepmodeaction
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;

    requires java.prefs;
    
    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.sleepmodeaction.SleepModeAction;
}