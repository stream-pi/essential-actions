package setmute;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class SetMute extends NormalAction {

    public SetMute() {
        setName("Set Mute");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));
    }

    @Override
    public void initProperties() throws Exception {
        Property sourceProperty = new Property("source", Type.STRING);
        sourceProperty.setDisplayName("Source");

        Property isMuteProperty = new Property("mute", Type.BOOLEAN);
        isMuteProperty.setDisplayName("Mute");
        
        addClientProperties(sourceProperty,isMuteProperty);
    }

    @Override
    public void initAction() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void onActionClicked() throws Exception {
        // TODO Auto-generated method stub

        OBSRemoteController controller = MotherConnection.getRemoteController();

        if (controller == null) {
            new StreamPiAlert("Is OBS Connected?",
                    "It seems there is no connection to OBS, please connect it in Settings", StreamPiAlertType.WARNING)
                            .show();
        } else {
            controller.setMute(getClientProperties().getSingleProperty("source").getStringValue(), getClientProperties().getSingleProperty("mute").getBoolValue(), MotherConnection.getDefaultCallBack(
                "Failed to mute source","Failed to do that"
            ));
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
