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

    public SetVolume() {
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

        Property setVolumeProperty = new Property("volume", Type.DOUBLE);
        setVolumeProperty.setDisplayName("Volume");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");
        
        addClientProperties(sourceProperty, setVolumeProperty, autoConnectProperty);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        double volume = getClientProperties().getSingleProperty("volume").getDoubleValue();

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setReplayBuffer(state));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setReplayBuffer(state);
        }

        OBSRemoteController controller = MotherConnection.getRemoteController();

        if (controller == null)
        {
            new StreamPiAlert("Is OBS Connected?",
                    "It seems there is no connection to OBS, please connect it in Settings", StreamPiAlertType.WARNING)
                            .show();
        } 
        else 
        {
            String source = getClientProperties().getSingleProperty("source").getStringValue();
            double volume = getClientProperties().getSingleProperty("volume").getDoubleValue();
            controller.setVolume(source, volume, MotherConnection.getDefaultCallBack("Failed to Set Volume","Failed to do that"));
        }
    }

    private void setVolume(state)
    {

    }
}
