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
        setVersion(new Version(1,0,1));
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property websiteUrl = new Property("websiteURL", Type.STRING);
        websiteUrl.setDisplayName("Website URL");
        websiteUrl.setDefaultValueStr("https://stream-pi.com/");
        websiteUrl.setCanBeBlank(false);

        addClientProperties(websiteUrl);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        Property website = getClientProperties().getSingleProperty("websiteURL");

        String urlToOpen = website.getStringValue();

        if(!urlToOpen.startsWith("https://") && !urlToOpen.startsWith("http://"))
        {
            urlToOpen = "https://" + urlToOpen;
        }

        try
        {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            {
                Desktop.getDesktop().browse(new URI(urlToOpen));
            }
            else
            {
                Runtime runtime = Runtime.getRuntime();
                String osName = System.getProperty("os.name").toLowerCase();
                if (osName.contains("mac"))
                {
                    runtime.exec("open " + urlToOpen);
                }
                else if (osName.contains("nix") || osName.contains("nux"))
                {
                    runtime.exec("xdg-open " + urlToOpen);
                }
                else
                {
                    throw new MinorException("Unable to open URL '"+urlToOpen+"'. Check if its correct.");
                }
            }
        }
        catch (Exception e)
        {
            throw new MinorException("Unable to open URL '"+urlToOpen+"'. Check if its correct.");
        }
    }
}
