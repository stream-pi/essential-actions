package Mother;

import Mother.MotherConnection.MotherConnection;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import net.twasi.obsremotejava.OBSRemoteController;
import com.StreamPi.Util.Alert.StreamPiAlert;
import com.StreamPi.Util.Alert.StreamPiAlertType;

public class OBSActionConnectionTask extends Task<Void> {

    String url, pass;
    Button connectDisconnectButton;

    public OBSActionConnectionTask(String url, String pass, Button connectDisconnectButton)
    {
        this.url = url;
        this.pass = pass;
        this.connectDisconnectButton = connectDisconnectButton;    
    }

    @Override
    protected Void call() throws Exception
    {
        try
        {
            setConnectDisconnectButtonDisable(true);
           
            if(!url.startsWith("ws://"))
            {
                new StreamPiAlert("Invalid URL","Please fix URL and try again", StreamPiAlertType.ERROR).show();
                return null;
            }

            if(pass.isEmpty() || pass.isBlank())
                pass = null;

            OBSRemoteController obsRemoteController = new OBSRemoteController(url, false, pass);
            
            if(obsRemoteController.isFailed())
            {
                new StreamPiAlert("Unable to Connect to OBS", "Is it even running? Make sure the websocket plugin is installed. Check Credentials too", StreamPiAlertType.ERROR).show();
            }

            obsRemoteController.registerConnectionFailedCallback(message->{
                setConnectDisconnectButtonText("Connect");
                new StreamPiAlert("Unable to Connect", "Unable to establish connection to WebSocket with provided crendentials", StreamPiAlertType.ERROR).show();
                MotherConnection.setRemoteController(null);
            });

            obsRemoteController.registerDisconnectCallback(onDisconnect->{
                setConnectDisconnectButtonText("Connect");
                MotherConnection.setRemoteController(null);
            });


            obsRemoteController.registerConnectCallback(onConnect->{
                setConnectDisconnectButtonText("Disconnect");
                MotherConnection.setRemoteController(obsRemoteController);
            });
        }
        catch (Exception e)
        {
            new StreamPiAlert("Unable to Connect", "Unable to establish connection to WebSocket with provided crendentials", StreamPiAlertType.ERROR).show();
            MotherConnection.setRemoteController(null);
            e.printStackTrace();
        }
        finally
        {
            setConnectDisconnectButtonDisable(false);
        }
        return null;
    }


    private void setConnectDisconnectButtonText(String text)
    {
        Platform.runLater(()-> connectDisconnectButton.setText(text));
    }

    private void setConnectDisconnectButtonDisable(boolean disable)
    {
        Platform.runLater(()-> connectDisconnectButton.setDisable(disable));
    }
    
}