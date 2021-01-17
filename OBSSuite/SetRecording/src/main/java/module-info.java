module com.StreamPi.OBSSuite.SetRecordingAction 
{
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires obs.websocket.java;
    requires com.StreamPi.OBSSuite.MotherAction;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with SetRecording.SetRecording;
}
