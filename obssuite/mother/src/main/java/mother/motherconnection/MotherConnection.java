package mother.motherconnection;

import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;

import mother.OBSActionConnectionTask;
import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.callbacks.Callback;

public class MotherConnection
{
    private static OBSRemoteController obsRemoteController = null;

    private static String url = null;
    private static String pass = null;

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

    public void connect()
    {
        connect(true);
    }

    public void connect(boolean runAsync)
    {
        new OBSActionConnectionTask( null, runAsync);
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
}
