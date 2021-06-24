package togglesourcevisibility;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

public class ToggleSourceVisibility extends ToggleAction
{
    public ToggleSourceVisibility()
    {
        setName("Toggle Source Visibility");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
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

    @Override
    public void initProperties() throws MinorException
    {
        Property sceneProperty = new Property("scene", Type.STRING);
        sceneProperty.setDisplayName("Scene");
        
        Property sourceProperty = new Property("source", Type.STRING);
        sourceProperty.setDisplayName("Source");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(sceneProperty, sourceProperty, autoConnectProperty);
    }

    public void onClicked(boolean visible) throws MinorException
    {
        String source = getClientProperties().getSingleProperty("source").getStringValue();
        String scene = getClientProperties().getSingleProperty("scene").getStringValue();

        if(source.isBlank())
        {
            throw new MinorException("Blank Source Name","No Source specified");
        }

        if(scene.isBlank())
        {
            throw new MinorException("Blank Scene Name","No Source specified");
        }

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setVisibility(scene, source, visible));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setVisibility(scene, source, visible);
        }
    }

    public void setVisibility(String scene, String source, boolean visible)
    {
        MotherConnection.getRemoteController().setSourceVisibility(scene, source, visible, setVisibilityResponse -> {
            String status = setVisibilityResponse.getStatus();
            String error = setVisibilityResponse.getError();

            if(status.equals("error"))
            {
                new StreamPiAlert("OBS",error, StreamPiAlertType.ERROR).show();
            }
        });

    }
}
