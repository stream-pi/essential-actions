package SetRecording;

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

public class SetRecording extends NormalAction {

    public SetRecording() {
        setName("Set Recording");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));

        states = new ArrayList<>();
        states.add("Start");
        states.add("Stop");
    }

    private ArrayList<String> states;

    @Override
    public void initProperties() throws Exception {

        Property recordingStatusProperty = new Property("recording_status", Type.LIST);
        recordingStatusProperty.setListValue(states);
        recordingStatusProperty.setDisplayName("Recording State");
        
        addClientProperties(recordingStatusProperty);
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
            
            String state = states.get(getClientProperties().getSingleProperty("recording_status").getSelectedIndex());

            if(state.equals("Start"))
            {
                controller.startRecording(MotherConnection.getDefaultCallBack("Failed to Start Recording","Failed to do that"));
            }
            else if(state.equals("Stop"))
            {
                controller.stopRecording(MotherConnection.getDefaultCallBack("Failed to Stop Recording","Failed to do that"));
            }
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
