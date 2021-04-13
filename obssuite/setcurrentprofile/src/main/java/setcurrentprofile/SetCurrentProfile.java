package setcurrentprofile;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class SetCurrentProfile extends NormalAction
{

    public SetCurrentProfile() {
        setName("Set Current Profile");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));
    }

    @Override
    public void initProperties() throws Exception {
        // TODO Auto-generated method stub

        Property currentProfileProperty = new Property("current_profile", Type.STRING);
        currentProfileProperty.setDisplayName("Profile Name");
        
        addClientProperties(currentProfileProperty);
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
            controller.setCurrentProfile(getClientProperties().getSingleProperty("current_profile").getStringValue(), MotherConnection.getDefaultCallBack(
                "Unable to Set Current Profile","Failed to set current profile"
            ));
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
