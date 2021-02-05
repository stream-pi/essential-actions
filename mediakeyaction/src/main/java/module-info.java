import com.stream_pi.mediakeyaction.MediaKeyAction;

module com.stream_pi.MediaKeyAction
{
    requires com.stream_pi.actionapi;
    requires com.stream_pi.util;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    provides com.stream_pi.actionapi.normalaction.NormalAction with MediaKeyAction;
}