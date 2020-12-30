module com.StreamPi.TwitterAction {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires org.twitter4j.core;

    requires java.desktop;

    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.TwitterAction.TwitterAction;
}