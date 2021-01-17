module com.StreamPi.OBSSuite.SetCurrentTransitionAction  {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires obs.websocket.java;
    requires com.StreamPi.OBSSuite.MotherAction;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with SetCurrentTransition.SetCurrentTransition;
}
