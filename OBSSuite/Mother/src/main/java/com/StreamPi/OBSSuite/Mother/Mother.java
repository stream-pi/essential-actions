package com.StreamPi.OBSSuite.Mother;

import com.StreamPi.ActionAPI.ActionProperty.ServerProperties;
import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.OBSSuite.Mother.API;
import com.StreamPi.OBSSuite.Mother.MotherInterface.MotherInterface;
import com.StreamPi.Util.Version.Version;

import javafx.application.Platform;
import javafx.scene.control.Button;

public class Mother extends NormalAction
{

    public Mother()
    {
        setName("OBS Plugin");
        setCategory("OBS");
        setVisibilityInPluginsPane(false);
        setAuthor("rnayabed");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));


        connectDisconnectButton = new Button("Connect");
        
        setButtonBar(connectDisconnectButton);
    }

    private Button connectDisconnectButton;

    @Override
    public void initProperties() throws Exception {
        Property urlProperty = new Property("url", Type.STRING);
        urlProperty.setDisplayName("Localhost URL");
        urlProperty.setCanBeBlank(false);

        Property passwordProperty = new Property("pass", Type.STRING);
        passwordProperty.setDisplayName("Connection Password");
       
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
                ServerProperties sp = getServerProperties();

                String url = sp.getSingleProperty("url").getStringValue();
                String pass = sp.getSingleProperty("pass").getStringValue();
        
                
                if(MotherInterface.getInstance() == null)
                {
                    connect(url, pass);
                } 
                else
                {
                    MotherInterface.getInstance().getRemoteController().disconnect();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        ServerProperties sp = getServerProperties();

        String url = sp.getSingleProperty("url").getStringValue();
        String pass = sp.getSingleProperty("pass").getStringValue();
    
            
        boolean connectOnStartupProperty = sp.getSingleProperty("connect_on_startup").getBoolValue();

        if(connectOnStartupProperty)
        {
            connect(url, pass);
        }
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
    
}
