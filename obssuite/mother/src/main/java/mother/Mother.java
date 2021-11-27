package mother;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import javafx.scene.control.Button;
import mother.motherconnection.MotherConnection;

public class Mother extends NormalAction
{

    public Mother()
    {
        setName("OBS Plugin");
        setCategory("OBS");
        setVisibilityInPluginsPane(false);
        setAuthor("rnayabed");
        setHelpLink("https://github.com/stream-pi/essential-actions/blob/master/obssuite/README.md");
        setVersion(MotherConnection.VERSION);

        connectDisconnectButton = new Button("Connect");
        
        setServerSettingsButtonBar(connectDisconnectButton);
    }

    @Override
    public void onActionClicked()
    {

    }

    private Button connectDisconnectButton;

    @Override
    public void initProperties() throws MinorException
    {
        Property urlProperty = new Property("url", Type.STRING);
        urlProperty.setDisplayName("URL");
        urlProperty.setDefaultValueStr("ws://localhost:4444");
        urlProperty.setCanBeBlank(false);

        Property passwordProperty = new Property("pass", Type.STRING);
        passwordProperty.setControlType(ControlType.TEXT_FIELD_MASKED);
        passwordProperty.setDisplayName("Password");
       
        Property connectOnStartupProperty = new Property("connect_on_startup", Type.BOOLEAN);
        connectOnStartupProperty.setDisplayName("Connect On Startup");
        connectOnStartupProperty.setDefaultValueBoolean(false);


        addServerProperties(
            urlProperty,
            passwordProperty,
            connectOnStartupProperty
        );
    }

    @Override
    public void initAction() throws MinorException
    {
        MotherConnection.setPass(getPassword());
        MotherConnection.setUrl(getURL());
        MotherConnection.setConnectDisconnectButton(connectDisconnectButton);

        connectDisconnectButton.setOnAction(action->{
            try
            {
                if(MotherConnection.getRemoteController() == null)
                {
                    connect(getURL(), getPassword());
                } 
                else
                {
                    MotherConnection.getRemoteController().disconnect();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        if(isConnectOnStartup() && firstRun)
        {
            connect(getURL(), getPassword());
        }

        firstRun = false;
    }

    private boolean firstRun = true;

    private String getURL() throws MinorException
    {
        return getServerProperties().getSingleProperty("url").getStringValue();
    }

    private String getPassword() throws MinorException
    {
        return getServerProperties().getSingleProperty("pass").getStringValue();
    }

    private boolean isConnectOnStartup() throws MinorException
    {
        return getServerProperties().getSingleProperty("connect_on_startup").getBoolValue();
    }

    private void connect(String url, String pass)
    {
        MotherConnection.setPass(pass);
        MotherConnection.setUrl(url);
        new OBSActionConnectionTask(true, null, null, null);
    }

    @Override
    public void onShutDown()
    {
        if(MotherConnection.getRemoteController() != null)
        {
            MotherConnection.getRemoteController().disconnect();
        }
    }
    
}
