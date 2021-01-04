package com.StreamPi.OBSSuite.Mother;

import com.StreamPi.OBSSuite.Mother.MotherInterface.MotherInterface;
import com.StreamPi.Util.Alert.StreamPiAlert;
import com.StreamPi.Util.Alert.StreamPiAlertType;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import net.twasi.obsremotejava.OBSRemoteController;

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
    protected Void call() throws Exception {

        try
        {
            setConnectDisconnectButtonDisable(true);

            if(pass.isEmpty() || pass.isBlank())
                pass = null;

            OBSRemoteController obsRemoteController = new OBSRemoteController(url, false, pass);

            obsRemoteController.registerConnectionFailedCallback(message->{
                setConnectDisconnectButtonText("Disconnect");
                new StreamPiAlert("Unable to Connect", "Unable to establish connection to WebSocket with provided crendentials", StreamPiAlertType.ERROR).show();
            });

            obsRemoteController.registerDisconnectCallback(onDisconnect->{
                setConnectDisconnectButtonText("Connect");
                MotherInterface.startNewInstance(null);
            });


            obsRemoteController.connect();

            obsRemoteController.registerConnectCallback(onConnect->{
                MotherInterface.startNewInstance(obsRemoteController);
            });
        }
        catch (Exception e)
        {
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
