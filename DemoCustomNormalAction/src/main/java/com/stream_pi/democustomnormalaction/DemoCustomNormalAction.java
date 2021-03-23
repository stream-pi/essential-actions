package com.stream_pi.democustomnormalaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.version.Version;

public class DemoCustomNormalAction extends NormalAction
{

    public DemoCustomNormalAction()
    {
        setName("Demo Action");
        setAuthor("rnayabed");
        setHelpLink("https://github.com/stream-pi/");
        setVersion(new Version(1,0,0));

        setCategory("Custom Actions");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property property1 = new Property("ClientServerProperty1", Type.STRING);
        property1.setDefaultValueStr("23");


        addClientProperties(
                property1
        );
    }


    @Override
    public void initAction()  {
        // This is called after initProperties()
    }

    @Override
    public void onActionClicked()
    {
        //Called when action is clicked

        System.out.println("Action Called!");
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}
