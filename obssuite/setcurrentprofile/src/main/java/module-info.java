module com.stream_pi.obssuite.setcurrentprofileaction
{
    requires com.stream_pi.actionapi;
    requires com.stream_pi.util;

    requires com.stream_pi.obssuite.motheraction;
    
    provides com.stream_pi.actionapi.normalaction.NormalAction with setcurrentprofile.SetCurrentProfile;
}
