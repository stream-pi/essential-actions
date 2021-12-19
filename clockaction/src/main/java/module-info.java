module in.rnayabed.digitalclockaction
{
    requires com.stream_pi.action_api;
    requires com.stream_pi.util;

    requires eu.hansolo.medusa;

    provides com.stream_pi.action_api.externalplugin.ExternalPlugin with digitalclockaction.DigitalClockAction;
}
