import com.stream_pi.textblockaction.TextBlockAction;

module com.stream_pi.textblockaction {
    requires com.stream_pi.actionapi;
    requires com.stream_pi.util;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires java.desktop;

    provides com.stream_pi.actionapi.normalaction.NormalAction with TextBlockAction;

}