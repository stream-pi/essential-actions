package togglestreaming;

import com.stream_pi.action_api.actionproperty.property.BooleanProperty;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;

import mother.motherconnection.MotherConnection;

public class ToggleStreaming extends ToggleAction
{

    public ToggleStreaming()
    {
        setName("Toggle Streaming");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void initProperties() throws Exception
    {
        BooleanProperty autoConnectProperty = new BooleanProperty("auto_connect");
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(autoConnectProperty);
    }

    @Override
    public void onToggleOn() throws Exception
    {
        onClicked(true);
    }

    @Override
    public void onToggleOff() throws Exception
    {
        onClicked(false);
    }

    public void onClicked(boolean streaming) throws MinorException
    {
        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setStreaming(streaming));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setStreaming(streaming);
        }
    }


    public void setStreaming(boolean enabled)
    {
        if(enabled)
        {
            MotherConnection.getRemoteController().startStreaming(startStreamingResponse -> {
                String status = startStreamingResponse.getStatus();
                String error = startStreamingResponse.getError();

                if(status.equals("error"))
                {
                    String content;

                    content = error;

                    new StreamPiAlert("OBS",content, StreamPiAlertType.ERROR).show();
                }
            });
        }
        else
        {
            MotherConnection.getRemoteController().stopStreaming(stopStreamingResponse -> {
                String status = stopStreamingResponse.getStatus();
                String error = stopStreamingResponse.getError();

                if(status.equals("error"))
                {
                    String content;

                    content = error;

                    new StreamPiAlert("OBS",content, StreamPiAlertType.ERROR).show();
                }
            });
        }

    }
}
