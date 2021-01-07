package com.StreamPi.WebsiteAction;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Exception.MinorException;
import com.StreamPi.Util.Version.Version;

import java.awt.*;
import java.net.URI;

public class WebsiteAction extends NormalAction {

    public WebsiteAction()
    {
        setName("Website");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-globe");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));
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
