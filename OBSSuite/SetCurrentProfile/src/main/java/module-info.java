module com.StreamPi.OBSSuite.SetCurrentProfile  {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires com.StreamPi.OBSSuite.Mother;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with SetCurrentProfile.SetCurrentProfile;
}
