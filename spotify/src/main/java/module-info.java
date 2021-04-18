module com.stream_pi.spotify
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;
    
    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.spotify.Spotify;
}