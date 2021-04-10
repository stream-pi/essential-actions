package mother.motherconnection;

import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;

import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;
import mother.OBSActionConnectionTask;
import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.callbacks.Callback;
import net.twasi.obsremotejava.requests.ResponseBase;

public class MotherConnection
{
    private static OBSRemoteController obsRemoteController = null;

    public static final Version VERSION = new Version(2,0,0);

    private static String url = null;
    private static String pass = null;

    private static Button connectDisconnectButton = null;

    public static void setConnectDisconnectButton(Button connectDisconnectButton)
    {
        MotherConnection.connectDisconnectButton = connectDisconnectButton;
    }

    public static Button getConnectDisconnectButton()
    {
        return connectDisconnectButton;
    }

    public static void setUrl(String url) {
        MotherConnection.url = url;
    }

    public static void setPass(String pass) {
        MotherConnection.pass = pass;
    }

    public static String getUrl() {
        return url;
    }

    public static String getPass() {
        return pass;
    }

    public static void connect()
    {
        connect(true, null, null, null);
    }

    public static void connect(Runnable onConnectRunnable)
    {
        connect(true, null, onConnectRunnable, null);
    }

    public static void connect(boolean runAsync, Runnable onFailToConnectRunnable,
                        Runnable onConnectRunnable, Runnable onDisconnectRunnable)
    {
        new OBSActionConnectionTask(runAsync, onFailToConnectRunnable,
                onConnectRunnable, onDisconnectRunnable);
    }

    public static OBSRemoteController getRemoteController()
    {
        return obsRemoteController;
    }

    public static void setRemoteController(OBSRemoteController obsRemoteController)
    {
        MotherConnection.obsRemoteController = obsRemoteController;
    }

    public static Callback getDefaultCallBack(String head, String content)
    {
        return response ->
        {
            if(response.getStatus().equals("error"))
            {
                new StreamPiAlert(head, content, StreamPiAlertType.ERROR).show();
            }
        };
    }

    public static void showOBSNotRunningError()
    {
        new StreamPiAlert("Is OBS Connected?",
                "It seems there is no connection to OBS, please connect it in Settings", StreamPiAlertType.WARNING)
                .show();
    }
}
