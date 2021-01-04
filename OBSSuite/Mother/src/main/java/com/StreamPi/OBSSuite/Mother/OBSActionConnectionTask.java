package com.StreamPi.OBSSuite.Mother;

import com.StreamPi.OBSSuite.Mother.MotherInterface.MotherInterface;
import com.StreamPi.Util.Alert.StreamPiAlert;
import com.StreamPi.Util.Alert.StreamPiAlertType;

import javafx.concurrent.Task;
import net.twasi.obsremotejava.OBSRemoteController;

public class OBSActionConnectionTask extends Task<Void> {

    String url, pass;
    boolean connectOnStartupProperty;

    public OBSActionConnectionTask(String url, String pass, boolean connectOnStartupProperty)
    {
        this.url = url;
        this.pass = pass;
        this.connectOnStartupProperty = connectOnStartupProperty;    
    }

    @Override
    protected Void call() throws Exception {

        try
        {
            if(pass.isEmpty() || pass.isBlank())
                pass = null;

            OBSRemoteController obsRemoteController = new OBSRemoteController(url, false, pass);

            obsRemoteController.registerConnectionFailedCallback(message->{
                new StreamPiAlert("Unable to Connect", "Unable to establish connection to WebSocket with provided crendentials", StreamPiAlertType.ERROR).show();
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

        return null;
    }
    
}
