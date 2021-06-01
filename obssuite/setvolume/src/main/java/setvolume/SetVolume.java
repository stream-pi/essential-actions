package setvolume;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class SetVolume extends NormalAction
{
    public SetVolume()
    {
        setName("Set Volume");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property sourceProperty = new Property("source", Type.STRING);
        sourceProperty.setDisplayName("Source");

        Property volumeProperty = new Property("volume", Type.DOUBLE);
        volumeProperty.setDisplayName("Volume");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");
        
        addClientProperties(sourceProperty, volumeProperty, autoConnectProperty);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        String source = getClientProperties().getSingleProperty("source").getStringValue();
        double volume = getClientProperties().getSingleProperty("volume").getDoubleValue();

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setVolume(source, volume));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setVolume(source, volume);
        }
    }

    private void setVolume(String source, double volume)
    {
        MotherConnection.getRemoteController().setVolume(source, volume, setVolumeResponse -> {
            if(setVolumeResponse.getError().equals("error"))
            {
                new StreamPiAlert("OBS",setVolumeResponse.getStatus(), StreamPiAlertType.ERROR).show();
            }
        });
    }
}
