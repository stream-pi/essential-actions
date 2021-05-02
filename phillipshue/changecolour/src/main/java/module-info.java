module com.stream_pi.phillipshue
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;

    requires io.github.zeroone3010.yahueapi

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.phillipshue.ChangeColour;
}