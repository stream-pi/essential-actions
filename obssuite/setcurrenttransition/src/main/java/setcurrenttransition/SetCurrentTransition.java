package setcurrenttransition;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

public class SetCurrentTransition extends NormalAction {

    public SetCurrentTransition()
    {
        setName("Set Current Transition");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property currentTransitionProperty = new Property("transition", Type.STRING);
        currentTransitionProperty.setDisplayName("Transition Name");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(currentTransitionProperty, autoConnectProperty);
    }


    @Override
    public void onActionClicked() throws MinorException
    {
        String transition = getClientProperties().getSingleProperty("transition").getStringValue();

        if(transition.isBlank())
        {
            throw new MinorException("Blank Transition Name","No Transition Name specified");
        }

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setTransition(transition));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setTransition(transition);
        }
    }


    public void setTransition(String transition)
    {
        MotherConnection.getRemoteController().setCurrentTransition(transition, setCurrentTransitionResponse -> {
            String status = setCurrentTransitionResponse.getStatus();
            String error = setCurrentTransitionResponse.getError();

            if(status.equals("error"))
            {
                String content;

                if(error.equals("transition does not exist"))
                {
                    content = "Transition "+transition+" does not exist.";
                }
                else
                {
                    content = error;
                }

                new StreamPiAlert("OBS",content, StreamPiAlertType.ERROR).show();
            }
        });
    }
}
