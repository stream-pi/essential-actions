package togglestudiomode;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;

import mother.motherconnection.MotherConnection;

public class ToggleStudioMode extends ToggleAction
{

    public ToggleStudioMode()
    {
        setName("Toggle Studio Mode");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(autoConnectProperty);
    }

    @Override
    public void onToggleOn() throws MinorException
    {
        onClicked(true);
    }

    @Override
    public void onToggleOff() throws MinorException
    {
        onClicked(false);
    }

    public void onClicked(boolean studioMode) throws MinorException
    {
        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setStudioMode(studioMode));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setStudioMode(studioMode);
        }
    }


    public void setStudioMode(boolean enabled)
    {
        MotherConnection.getRemoteController().setStudioModeEnabled(enabled, setStudioModeEnabledResponse -> {
            String status = setStudioModeEnabledResponse.getStatus();
            String error = setStudioModeEnabledResponse.getError();

            if(status.equals("error"))
            {
                String content;

                content = error;

                new StreamPiAlert("OBS",content, StreamPiAlertType.ERROR).show();
            }
        });
    }
    
}
