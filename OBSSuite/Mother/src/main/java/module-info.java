module com.StreamPi.OBSSuite.Mother
{
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires transitive obs.websocket.java;

    exports com.StreamPi.OBSSuite.Mother.MotherInterface;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.OBSSuite.Mother.Mother;
}