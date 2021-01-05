package SetMute;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import Mother.MotherConnection.MotherConnection;
import com.StreamPi.Util.Alert.StreamPiAlert;
import com.StreamPi.Util.Alert.StreamPiAlertType;
import com.StreamPi.Util.Version.Version;

import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.callbacks.Callback;
import net.twasi.obsremotejava.requests.ResponseBase;

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
        sourceProperty.setDisplayName("Transition Name");
        sourceProperty.setCanBeBlank(false);

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
