package com.stream_pi.iphoneairplay;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

public class iPhoneAirplay extends NormalAction
{

    public iPhoneAirplay()
    {
        setName("iPhone Airplay");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/");
        setVersion(new Version(1,6,9));

        setCategory("Exclusive Actions");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property property1 = new Property("serverName", Type.STRING);
        property1.setDefaultValueStr("StreamPi");


        addClientProperties(
                property1
        );
    }


    @Override
    public void initAction()  {

    }


    private static boolean serverActive = false;

    @Override
    public void onActionClicked()
    {
        if(serverActive != true)
        {
            Property property1 = getClientProperties().getSingleProperty("serverName");

            String serverName = property1.getStringValue();
            int airPlayPort = 5001;
            int airTunesPort = 7001;
            AirPlayBonjour airPlayBonjour = new AirPlayBonjour(serverName);
            airPlayBonjour.start(airPlayPort, airTunesPort);
            serverActive = true;
        }
        else
        {
            airPlayBonjour.stop();
        
            serverActive = false;
        }
    }
}
