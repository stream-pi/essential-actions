module SetCurrentScene {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires obs.websocket.java;
    requires com.StreamPi.OBSSuite.Mother;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with SetCurrentScene.SetCurrentScene;
}
