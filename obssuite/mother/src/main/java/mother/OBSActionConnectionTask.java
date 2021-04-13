package mother;

import mother.motherconnection.MotherConnection;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import net.twasi.obsremotejava.OBSRemoteController;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;

public class OBSActionConnectionTask extends Task<Void>
{

    String url, pass;
    Button connectDisconnectButton;

    public OBSActionConnectionTask(Button connectDisconnectButton,
                                   boolean runAsync)
    {
        this.url = MotherConnection.getUrl();
        this.pass = MotherConnection.getPass();
        this.connectDisconnectButton = connectDisconnectButton;

        if(runAsync)
        {
            new Thread(this).start();
        }
        else
        {
            call();
        }
    }

    @Override
    protected Void call()
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
                new StreamPiAlert("Unable to Connect", "Unable to establish connection to WebSocket with provided crendentials\n\n"+
                    "Detailed Error : "+message, StreamPiAlertType.ERROR).show();
                MotherConnection.setRemoteController(null);
            });

            obsRemoteController.registerDisconnectCallback(()->{
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
        if(connectDisconnectButton == null)
            return;

        Platform.runLater(()-> connectDisconnectButton.setText(text));
    }

    private void setConnectDisconnectButtonDisable(boolean disable)
    {
        if(connectDisconnectButton == null)
            return;

        Platform.runLater(()-> connectDisconnectButton.setDisable(disable));
    }
    
}