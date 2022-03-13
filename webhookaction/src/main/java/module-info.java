import com.stream_pi.webhookaction.WebhookAction;

module com.stream_pi.webhookaction
{
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires java.desktop;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with WebhookAction;
}