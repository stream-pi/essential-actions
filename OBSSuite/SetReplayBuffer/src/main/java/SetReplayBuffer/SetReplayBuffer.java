package SetReplayBuffer;

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

public class SetReplayBuffer extends NormalAction {

    public SetReplayBuffer() {
        setName("Set Replay Buffer");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));

        states = new ArrayList<>();
        states.add("Start");
        states.add("Stop");
        states.add("Save");
    }

    private ArrayList<String> states;

    @Override
    public void initProperties() throws Exception {

        Property replayStatusActionProperty = new Property("replay_status", Type.LIST);
        replayStatusActionProperty.setListValue(states);
        replayStatusActionProperty.setDisplayName("Replay Buffer State");

        
        addClientProperties(replayStatusActionProperty);
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
            
            String state = states.get(getClientProperties().getSingleProperty("replay_status").getSelectedIndex());

            if(state.equals("Start"))
            {
                controller.startReplayBuffer(MotherConnection.getDefaultCallBack("Failed to Start Replay Buffer","Failed to do that"));
            }
            else if(state.equals("Stop"))
            {
                controller.stopReplayBuffer(MotherConnection.getDefaultCallBack("Failed to Stop Replay Buffer","Failed to do that"));
            }
            else if(state.equals("Save"))
            {
                controller.saveReplayBuffer(MotherConnection.getDefaultCallBack("Failed to Save Replay Buffer","Failed to do that"));
            }
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
