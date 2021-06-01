package setcurrentscene;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import mother.motherconnection.MotherConnection;

public class SetCurrentScene extends NormalAction
{
    public SetCurrentScene()
    {
        setName("Set Current Scene");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
        setAuthor("rnayabed");
        setVersion(MotherConnection.VERSION);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property currentSceneProperty = new Property("scene", Type.STRING);
        currentSceneProperty.setDisplayName("Scene Name");

        Property autoConnectProperty = new Property("auto_connect", Type.BOOLEAN);
        autoConnectProperty.setDefaultValueBoolean(true);
        autoConnectProperty.setDisplayName("Auto Connect if not connected");

        addClientProperties(currentSceneProperty, autoConnectProperty);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        String scene = getClientProperties().getSingleProperty("scene").getStringValue();

        if(scene.isBlank())
        {
            throw new MinorException("Blank Scene Name","No Scene Name specified");
        }

        if (MotherConnection.getRemoteController() == null)
        {
            boolean autoConnect = getClientProperties().getSingleProperty(
                    "auto_connect"
            ).getBoolValue();

            if(autoConnect)
            {
                MotherConnection.connect(()->setScene(scene));
            }
            else
            {
                MotherConnection.showOBSNotRunningError();
            }
        }
        else
        {
            setScene(scene);
        }
    }

    public void setScene(String scene)
    {
        MotherConnection.getRemoteController().setCurrentScene(scene, setCurrentSceneResponse -> {
            String status = setCurrentSceneResponse.getStatus();
            String error = setCurrentSceneResponse.getError();

            if(status.equals("error"))
            {
                String content;

                if(error.equals("scene does not exist"))
                {
                    content = "Scene "+scene+" does not exist.";
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
