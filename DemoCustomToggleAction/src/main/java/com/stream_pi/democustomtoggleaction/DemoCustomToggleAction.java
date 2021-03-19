package com.stream_pi.democustomtoggleaction;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import java.util.ArrayList;

public class DemoCustomToggleAction extends ToggleAction
{

    public DemoCustomToggleAction()
    {
        setName("Demo Action");
        setAuthor("dubbadhar");
        setHelpLink("https://github.com/Stream-Pi/");
        setVersion(new Version(1,0,0));

        setCategory("Custom Actions");

    }

    @Override
    public void onToggleOn() throws Exception {
        new StreamPiAlert("Alert", "Toggle ON").show();
    }

    @Override
    public void onToggleOff() throws Exception {
        new StreamPiAlert("Alert", "Toggle OFF").show();
    }

    @Override
    public void initProperties() throws Exception {
        //Called First


    }


    @Override
    public void initAction()  {
        // This is called after initProperties()
    }


    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}
