package mother;

import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import javafx.application.Platform;
import javafx.concurrent.Task;
import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class OBSActionConnectionTask extends Task<Void>
{
    private Runnable onFailToConnectRunnable = null;
    private Runnable onConnectRunnable = null;
    private Runnable onDisconnectRunnable = null;

    public OBSActionConnectionTask(boolean runAsync, Runnable onFailToConnectRunnable,
                                   Runnable onConnectRunnable, Runnable onDisconnectRunnable)
    {
        this.onFailToConnectRunnable = onFailToConnectRunnable;
        this.onConnectRunnable = onConnectRunnable;
        this.onDisconnectRunnable = onDisconnectRunnable;

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
            String url = MotherConnection.getUrl();
            String pass = MotherConnection.getPass();

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

                if(onFailToConnectRunnable != null)
                {
                    onFailToConnectRunnable.run();
                    onFailToConnectRunnable = null;
                }
            });

            obsRemoteController.registerDisconnectCallback(()->{
                setConnectDisconnectButtonText("Connect");
                MotherConnection.setRemoteController(null);

                if(onDisconnectRunnable != null)
                {
                    onDisconnectRunnable.run();
                    onDisconnectRunnable = null;
                }
            });


            obsRemoteController.registerConnectCallback(onConnect->{
                setConnectDisconnectButtonText("Disconnect");
                MotherConnection.setRemoteController(obsRemoteController);

                if(onConnectRunnable != null)
                {
                    onConnectRunnable.run();
                    onConnectRunnable = null;
                }
            });
        }
        catch (Exception e)
        {
            MotherConnection.showOBSNotRunningError();
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
        if(MotherConnection.getConnectDisconnectButton() == null)
            return;

        Platform.runLater(()-> MotherConnection.getConnectDisconnectButton().setText(text));
    }

    private void setConnectDisconnectButtonDisable(boolean disable)
    {
        if(MotherConnection.getConnectDisconnectButton() == null)
            return;

        Platform.runLater(()-> MotherConnection.getConnectDisconnectButton().setDisable(disable));
    }
    
}