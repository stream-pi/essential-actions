module com.stream_pi.obssuite.motheraction
{
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires transitive obs.websocket.java;
    requires transitive org.eclipse.jetty.client;
    requires transitive org.eclipse.jetty.websocket.api;
    requires transitive org.eclipse.jetty.http;
    requires transitive org.eclipse.jetty.util;
    requires transitive org.eclipse.jetty.websocket.client;
    requires transitive org.eclipse.jetty.websocket.common;
    requires transitive org.eclipse.jetty.io;

    requires transitive com.google.gson;

    exports mother.motherconnection;
    
    provides com.stream_pi.action_api.normalaction.NormalAction with mother.Mother;
}