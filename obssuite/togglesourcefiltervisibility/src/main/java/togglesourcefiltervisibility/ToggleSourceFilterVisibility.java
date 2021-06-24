package togglesourcefiltervisibility;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

public class ToggleSourceFilterVisibility extends ToggleAction
{
    public ToggleSourceFilterVisibility()
    {
        setName("Toggle Source Filter Visibility");
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
        Property sourceProperty = new Property("source", Type.STRING);
        sourceProperty.setDisplayName("Source");

        Property filterProperty = new Property("filter", Type.STRING);
        filterProperty.setDisplayName("Filter");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(sourceProperty, filterProperty, autoConnectProperty);
    }

    public void onClicked(boolean visible) throws MinorException
    {
        String source = getClientProperties().getSingleProperty("source").getStringValue();
        String filter = getClientProperties().getSingleProperty("filter").getStringValue();

        if(source.isBlank())
        {
            throw new MinorException("Blank Source Name","No Source specified");
        }

        if(filter.isBlank())
        {
            throw new MinorException("Blank Filter Name","No Source specified");
        }

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setVisibility(source, filter, visible));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setVisibility(source, filter, visible);
        }
    }

    public void setVisibility(String source, String filter, boolean visible)
    {
        MotherConnection.getRemoteController().setSourceFilterVisibility(source, filter, visible, setVisibilityResponse -> {
            String status = setVisibilityResponse.getStatus();
            String error = setVisibilityResponse.getError();

            if(status.equals("error"))
            {
                new StreamPiAlert("OBS",error, StreamPiAlertType.ERROR).show();
            }
        });

    }
}
