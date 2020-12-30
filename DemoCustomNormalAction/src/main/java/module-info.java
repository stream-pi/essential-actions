module com.StreamPi.DemoCustomNormalAction {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires org.kordamp.ikonli.javafx;

    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.DemoCustomNormalAction.DemoCustomNormalAction;
}