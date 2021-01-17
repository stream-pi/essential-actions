module com.StreamPi.OBSSuite.SetCurrentProfileAction  {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires com.StreamPi.OBSSuite.MotherAction;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with SetCurrentProfile.SetCurrentProfile;
}
