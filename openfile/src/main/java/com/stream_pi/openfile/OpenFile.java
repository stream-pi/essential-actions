package com.stream_pi.openfile;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.FileExtensionFilter;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import java.io.File;
import java.awt.*;
import java.net.URI;
import java.io.IOException;

public class OpenFile extends NormalAction
{

    public OpenFile()
    {
        setName("Open Files");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));
        setServerButtonGraphic("fas-folder-open");
        setCategory("Essentials");

    }

    @Override
    public void initProperties() throws MinorException {
        //Called First

        Property property1 = new Property("openfile", Type.STRING);
        property1.setDefaultValueStr("*.*");
        property1.setControlType(ControlType.FILE_PATH);
        property1.setDisplayName("Document File Location");
        property1.setExtensionFilters(
                new FileExtensionFilter("File","*.*")
        );

        addClientProperties(
                property1
        );
    }


    @Override
    public void initAction()  {

    }

    public String path = null;

    @Override
    public void onActionClicked() throws MinorException
    {
        try {
            showfile();
          }
          catch(IOException e) {
            e.printStackTrace();
          }
    }
    public void showfile() throws MinorException, IOException
    {
        Property fileloc = getClientProperties().getSingleProperty("openfile");

        if (fileloc.getStringValue().isBlank())
        {
            throw new MinorException("No file specified");
        }

        File file = new File(fileloc.getStringValue());

        Desktop.getDesktop().open(file);
    }

}
