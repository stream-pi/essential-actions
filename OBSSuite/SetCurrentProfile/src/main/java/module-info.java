module CurrentProfile {
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    requires com.StreamPi.OBSSuite.Mother;
    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with CurrentProfile.CurrentProfile;
}
