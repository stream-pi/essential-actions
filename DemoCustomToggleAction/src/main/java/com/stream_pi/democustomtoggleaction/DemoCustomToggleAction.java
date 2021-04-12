package com.stream_pi.democustomtoggleaction;

import com.stream_pi.action_api.action.DisplayTextAlignment;
import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.concurrent.Task;

import java.util.ArrayList;

public class DemoCustomToggleAction extends ToggleAction
{

    public DemoCustomToggleAction()
    {
        setName("Demo Toggle Action");
        setAuthor("rnayabed");
        setHelpLink("https://github.com/Stream-Pi/");
        setVersion(new Version(1,0,0));

        setCategory("Custom Actions");

    }

    @Override
    public void onToggleOn() throws Exception
    {

    }

    @Override
    public void onActionCreate()
    {
        new StreamPiAlert("Toggle was turned on!").show();
    }

    @Override
    public void onToggleOff()
    {
        new StreamPiAlert("Toggle was turned off!").show();
    }



    @Override
    public void initProperties()
    {
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
