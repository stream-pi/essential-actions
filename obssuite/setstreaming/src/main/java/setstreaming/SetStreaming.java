package setstreaming;

import java.util.ArrayList;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class SetStreaming extends NormalAction {

    public SetStreaming() {
        setName("Set Streaming");
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

        Property streamingStatusProperty = new Property("streaming_status", Type.LIST);
        streamingStatusProperty.setListValue(states);
        streamingStatusProperty.setDisplayName("Streaming State");
        
        addClientProperties(streamingStatusProperty);
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
            
            String state = states.get(getClientProperties().getSingleProperty("streaming_status").getSelectedIndex());

            if(state.equals("Start"))
            {
                controller.startStreaming(MotherConnection.getDefaultCallBack("Failed to Start Streaming","Failed to do that"));
            }
            else if(state.equals("Stop"))
            {
                controller.stopStreaming(MotherConnection.getDefaultCallBack("Failed to Stop Streaming","Failed to do that"));
            }
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
