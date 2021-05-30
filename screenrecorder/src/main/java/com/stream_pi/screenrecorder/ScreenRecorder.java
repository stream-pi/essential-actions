package com.stream_pi.screenrecorder;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

import com.goxr3plus.xr3capture;

public class ScreenRecorder extends ToggleAction
{

    public ScreenRecorder()
    {
        setName("Stream-Pi Screen Recorder");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/stream-pi/");
        setVersion(new Version(1,0,0));

        setCategory("Essentials");

    }

    public XR3Capture sr = new XR3Capture();

    @Override
    public void onToggleOn() throws Exception
    {
        sr.start();
    }

    @Override
    public void onActionCreate()
    {
        sr.start();
    }

    @Override
    public void onToggleOff()
    {
        sr.stop();
    }

}
