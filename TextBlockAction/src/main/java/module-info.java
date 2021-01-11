module com.StreamPi.TextBlockAction {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires java.desktop;

    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.TextBlockAction.TextBlockAction;

}