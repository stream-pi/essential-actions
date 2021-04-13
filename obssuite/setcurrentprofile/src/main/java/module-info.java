module com.stream_pi.obssuite.setcurrentprofileaction
{
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires com.stream_pi.obssuite.motheraction;
    
    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with setcurrentprofile.SetCurrentProfile;
}
