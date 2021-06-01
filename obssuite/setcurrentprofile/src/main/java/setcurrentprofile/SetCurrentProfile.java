package setcurrentprofile;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;

import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

public class SetCurrentProfile extends NormalAction
{
    public SetCurrentProfile()
    {
        setName("Set Current Profile");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property currentProfileProperty = new Property("profile", Type.STRING);
        currentProfileProperty.setDisplayName("Profile Name");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");
        
        addClientProperties(currentProfileProperty, autoConnectProperty);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        String profile = getClientProperties().getSingleProperty("profile").getStringValue();

        if(profile.isBlank())
        {
            throw new MinorException("Blank Profile Name","No Profile Name specified");
        }

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setProfile(profile));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setProfile(profile);
        }
    }

    public void setProfile(String profile)
    {
        MotherConnection.getRemoteController().setCurrentProfile(profile, setCurrentProfileResponse -> {
            String status = setCurrentProfileResponse.getStatus();
            String error = setCurrentProfileResponse.getError();

            if(status.equals("error"))
            {
                String content;

                if(error.equals("profile does not exist"))
                {
                    content = "Profile "+profile+" does not exist.";
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
