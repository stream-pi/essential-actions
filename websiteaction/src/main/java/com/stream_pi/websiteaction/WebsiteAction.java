package com.stream_pi.websiteaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import java.awt.*;
import java.net.URI;

public class WebsiteAction extends NormalAction
{

    public WebsiteAction()
    {
        setName("Website");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-globe");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void onActionSavedFromServer() throws Exception
    {
        String website = getClientProperties().getSingleProperty("websiteURL").getStringValue();

        if(website != null)
        {
            if(website.contains("google.com"))
            setDisplayText("GOOGLE");
            saveClientAction();
        }
    }

    @Override
    public void initProperties() throws Exception {
        Property websiteUrl = new Property("websiteURL", Type.STRING);
        websiteUrl.setDisplayName("Website URL");
        websiteUrl.setDefaultValueStr("https://stream-pi.com/");
        websiteUrl.setCanBeBlank(false);

        addClientProperties(websiteUrl);
    }


    @Override
    public void initAction() throws Exception {

    }

    @Override
    public void onActionClicked() throws Exception {
        Property website = getClientProperties().getSingleProperty("websiteURL");

        String urlToOpen = website.getStringValue();

        if(!urlToOpen.startsWith("https://") && !urlToOpen.startsWith("http://"))
        {
            urlToOpen = "https://" + urlToOpen;
        }

        try
        {
            Desktop.getDesktop().browse(new URI(urlToOpen));
        }
        catch (Exception e)
        {
            throw new MinorException("Unable to open URL '"+urlToOpen+"'. Check if its correct.");
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}
