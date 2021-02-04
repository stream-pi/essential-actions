module com.stream_pi.democustomnormalaction{
    requires com.stream_pi.actionapi;
    requires com.stream_pi.util;

    requires org.kordamp.ikonli.javafx;

    provides com.stream_pi.actionapi.normalaction.NormalAction with com.stream_pi.democustomnormalaction.DemoCustomNormalAction;
}