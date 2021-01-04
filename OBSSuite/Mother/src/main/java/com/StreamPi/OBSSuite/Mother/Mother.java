package com.StreamPi.OBSSuite.Mother;

import com.StreamPi.ActionAPI.ActionProperty.ServerProperties;
import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.OBSSuite.Mother.API;
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


        connectDisconnectButton = new Button();
        
        setButtonBar(connectDisconnectButton);
    }

    private Button connectDisconnectButton;

    private void setConnectDisconnectButtonText(String text)
    {
        Platform.runLater(()->{
            connectDisconnectButton.setText(text);
        });
    }

    @Override
    public void initProperties() throws Exception {
        // TODO Auto-generated method stub

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
        ServerProperties sp = getServerProperties();

        String url = sp.getSingleProperty("url").getStringValue();
        String pass = sp.getSingleProperty("pass").getStringValue();
    
        boolean connectOnStartupProperty = sp.getSingleProperty("connect_on_startup").getBoolValue();

        new Thread(
            new OBSActionConnectionTask(url, pass, connectOnStartupProperty)
        ).start();
    }

    @Override
    public void onActionClicked() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
