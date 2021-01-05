module com.StreamPi.OBSSuite.Mother
{
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires transitive obs.websocket.java;
    requires transitive org.eclipse.jetty.client;

    requires transitive com.google.gson;

    exports com.StreamPi.OBSSuite.Mother.MotherConnection;    
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.OBSSuite.Mother.Mother;
}