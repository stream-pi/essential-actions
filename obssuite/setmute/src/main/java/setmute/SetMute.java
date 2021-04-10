package setmute;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import mother.motherconnection.MotherConnection;
import net.twasi.obsremotejava.OBSRemoteController;

public class SetMute extends ToggleAction
{
    public SetMute()
    {
        setName("Set Mute");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
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

    @Override
    public void initProperties() throws Exception
    {
        Property sourceProperty = new Property("source", Type.STRING);
        sourceProperty.setDisplayName("Source");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(sourceProperty, autoConnectProperty);
    }

    public void onClicked(boolean mute) throws MinorException
    {
        String source = getClientProperties().getSingleProperty("source").getStringValue();

        if(source.isBlank())
        {
            throw new MinorException("Blank Source Name","No Source specified");
        }

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setMute(source, mute));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setMute(source, mute);
        }
    }

    public void setMute(String scene, boolean mute)
    {
        MotherConnection.getRemoteController().setMute(scene, mute, setMuteResponse -> {
            String status = setMuteResponse.getStatus();
            String error = setMuteResponse.getError();

            if(status.equals("error"))
            {
                String content;

                if(error.equals("source does not exist"))
                {
                    content = "Source "+scene+" does not exist.";
                }
                else
                {
                    content = error;
                }

                new StreamPiAlert("OBS",content, StreamPiAlertType.ERROR).show();
            }
        });
    }
}
