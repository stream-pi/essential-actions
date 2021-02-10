package mother;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.version.Version;
import com.stream_pi.util.exception.MinorException;

import mother.motherconnection.MotherConnection;


import javafx.scene.control.Button;

public class Mother extends NormalAction
{

    public Mother()
    {
        setName("OBS Plugin");
        setCategory("OBS");
        setVisibilityInPluginsPane(false);
        setAuthor("rnayabed");
        setHelpLink("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));


        connectDisconnectButton = new Button("Connect");
        
        setButtonBar(connectDisconnectButton);
    }

    private Button connectDisconnectButton;

    @Override
    public void initProperties() throws Exception {
        Property urlProperty = new Property("url", Type.STRING);
        urlProperty.setDisplayName("URL");
        urlProperty.setDefaultValueStr("ws://localhost:4444");
        urlProperty.setCanBeBlank(false);

        Property passwordProperty = new Property("pass", Type.STRING);
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
    public void initAction() throws Exception {

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
        new Thread(
            new OBSActionConnectionTask(url, pass, connectDisconnectButton)
        ).start();
    }

    @Override
    public void onActionClicked() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void onShutDown() throws Exception
    {
        if(MotherConnection.getRemoteController() != null)
        {
            MotherConnection.getRemoteController().disconnect();
        }
    }
    
}
