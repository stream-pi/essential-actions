package mother.motherconnection;

import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;

import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.callbacks.Callback;
import net.twasi.obsremotejava.requests.ResponseBase;

public class MotherConnection
{
    private static OBSRemoteController obsRemoteController = null;

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
