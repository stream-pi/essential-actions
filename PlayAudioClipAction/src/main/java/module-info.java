module com.StreamPi.PlayAudioClipAction {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires javafx.media;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.PlayAudioClipAction.PlayAudioClipAction;

}