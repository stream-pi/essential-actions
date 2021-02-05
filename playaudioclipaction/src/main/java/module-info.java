module com.stream_pi.playaudioclipaction
{
    requires com.stream_pi.actionapi;
    requires com.stream_pi.util;

    requires javafx.media;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    provides com.stream_pi.actionapi.normalaction.NormalAction with com.stream_pi.playaudioclipaction.PlayAudioClipAction;
}