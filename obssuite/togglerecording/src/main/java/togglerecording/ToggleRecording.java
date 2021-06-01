package togglerecording;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

public class ToggleRecording extends ToggleAction
{
    public ToggleRecording()
    {
        setName("Toggle Recording");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void onToggleOn() throws MinorException
    {
        onClicked(true);
    }

    @Override
    public void onToggleOff() throws MinorException
    {
        onClicked(false);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(autoConnectProperty);
    }

    public void onClicked(boolean record) throws MinorException
    {
        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setRecording(record));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setRecording(record);
        }
    }

    public void setRecording(boolean recording)
    {
        if(recording)
        {
            MotherConnection.getRemoteController().startRecording(setRecordingResponse -> {
                String status = setRecordingResponse.getStatus();
                String error = setRecordingResponse.getError();

                errorHandler(status, error);
            });
        }
        else
        {
            MotherConnection.getRemoteController().stopRecording(setRecordingResponse -> {
                String status = setRecordingResponse.getStatus();
                String error = setRecordingResponse.getError();

                errorHandler(status, error);
            });
        }
    }

    private void errorHandler(String status, String error)
    {
        if(status.equals("error"))
        {
            new StreamPiAlert("OBS",error, StreamPiAlertType.ERROR).show();
        }
    }
}
