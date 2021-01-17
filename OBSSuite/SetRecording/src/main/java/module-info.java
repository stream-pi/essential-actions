module com.StreamPi.OBSSuite.SetRecording 
{
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires obs.websocket.java;
    requires com.StreamPi.OBSSuite.Mother;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with SetRecording.SetRecording;
}
