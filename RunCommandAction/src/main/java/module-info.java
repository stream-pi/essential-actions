module com.StreamPi.RunCommandAction {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.RunCommandAction.RunCommandAction;

}