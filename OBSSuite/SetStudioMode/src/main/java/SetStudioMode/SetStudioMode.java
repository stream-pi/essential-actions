package SetStudioMode;

import java.util.ArrayList;

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
import net.twasi.obsremotejava.requests.SetCurrentScene.SetCurrentSceneResponse;

public class SetStudioMode extends NormalAction {

    public SetStudioMode() {
        setName("Set Studio Mode");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));
    }

    @Override
    public void initProperties() throws Exception {

        Property studioModeStatusProperty = new Property("studio_mode_status", Type.BOOLEAN);
        studioModeStatusProperty.setDisplayName("Studio Mode");
        
        addClientProperties(studioModeStatusProperty);
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
        } else 
        {
            controller.setStudioModeEnabled(getClientProperties().getSingleProperty("studio_mode_status").getBoolValue(),
            MotherConnection.getDefaultCallBack("Failed to set studio mode","Failed to do that"));
            
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
