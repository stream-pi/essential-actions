package currentfps;

import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

import java.util.List;
import java.util.Arrays;
import java.util.logging.Logger;

public class CurrentFPS extends NormalAction
{
    public CurrentFPS()
    {
        setName("OBS Current FPS");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("quimodotcom");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect();
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            getStats();
        }
    }

    public void getStats() throws MinorException
    {
        MotherConnection.getMessageSender().sendMessage(MotherConnection.setRequest("GET_STATS"));
    }
}
