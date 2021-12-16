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
        setVersion(new Version(2,0,0));
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property websiteUrl = new Property("url", Type.STRING);
        websiteUrl.setDisplayName("Website URL");
        websiteUrl.setDefaultValueStr("https://stream-pi.com/");
        websiteUrl.setCanBeBlank(false);

        addClientProperties(websiteUrl);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        getHostServices().showDocument(
                getClientProperties().getSingleProperty("url").getStringValue()
        );
    }
}
