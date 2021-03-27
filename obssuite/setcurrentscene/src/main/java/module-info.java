module com.stream_pi.obssuite.setcurrentsceneaction
{
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires obs.websocket.java;
    requires com.stream_pi.obssuite.motheraction;
    
    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with setcurrentscene.SetCurrentScene;
}
