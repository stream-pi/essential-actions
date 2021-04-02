module com.stream_pi.twitteraction {
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires org.twitter4j.core;

    requires java.desktop;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.twitteraction.TwitterAction;
}