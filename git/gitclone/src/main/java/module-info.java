module com.stream_pi.gitclone
{
    requires com.stream_pi.action_api;
    requires org.kordamp.ikonli.javafx;
    requires org.eclipse.jgit;
    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with com.stream_pi.gitclone.GitClone;
}
