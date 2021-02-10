package setcurrenttransition;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class SetCurrentTransition extends NormalAction {

    public SetCurrentTransition() {
        setName("Set Current Transition");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));
    }

    @Override
    public void initProperties() throws Exception {
        Property currentTransitionProperty = new Property("current_transition", Type.STRING);
        currentTransitionProperty.setDisplayName("Transition Name");
        
        addClientProperties(currentTransitionProperty);
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
            controller.setCurrentTransition(getClientProperties().getSingleProperty("current_transition").getStringValue(), MotherConnection.getDefaultCallBack(
                "Unable to Set Current Transition","Failed to set current Transition"
            ));
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
