module com.StreamPi.OBSSuite.MotherAction
{
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires transitive obs.websocket.java;
    requires transitive org.eclipse.jetty.client;
    requires transitive org.eclipse.jetty.websocket.api;
    requires transitive org.eclipse.jetty.http;
    requires transitive org.eclipse.jetty.util;
    requires transitive org.eclipse.jetty.websocket.client;
    requires transitive org.eclipse.jetty.websocket.common;
    requires transitive org.eclipse.jetty.io;

    requires transitive com.google.gson;

    exports Mother.MotherConnection;    
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with Mother.Mother;
}