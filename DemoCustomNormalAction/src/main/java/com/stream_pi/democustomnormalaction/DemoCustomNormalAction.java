package com.stream_pi.democustomnormalaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

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

        Button b1 = new Button("Test Alert");
        b1.setOnAction(actionEvent -> {
            new StreamPiAlert("Hi","Hello").show();
        });

        Button b2 = new Button("Modify Test");
        b2.setOnAction(actionEvent -> {
            try
            {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAA : "+getProfileID());
                getClientProperties().getSingleProperty("ClientServerProperty1")
                        .setStringValue("This property was dynamically modified");

                setDisplayText("Dynamic");

                saveClientAction();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });




        setClientActionSettingsButtonBar(b1,b2);
    }

    @Override
    public void onActionClicked()
    {
        //Called when action is clicked

        System.out.println("Action Called!");
    }
}
