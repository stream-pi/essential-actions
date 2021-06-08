package com.stream_pi.openfileaction;

import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import java.io.File;
import java.awt.*;
import java.net.URI;
import java.io.IOException;

public class OpenFileAction extends NormalAction
{
    public OpenFileAction()
    {
        setName("Open Files");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));
        setServerButtonGraphic("fas-folder-open");
        setCategory("Essentials");
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property fileLocationProperty = new StringProperty("file_location");
        fileLocationProperty.setControlType(ControlType.FILE_PATH);
        fileLocationProperty.setDisplayName("Document File Location");
        fileLocationProperty.setExtensionFilters(
                new FileExtensionFilter("File","*.*")
        );

        addClientProperties(fileLocationProperty);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        showFile();
    }

    public void showFile() throws MinorException
    {
        Property fileLocation = getClientProperties().getSingleProperty("file_location");

        if (fileLocation.getStringValue().isBlank())
        {
            throw new MinorException("No file specified");
        }

        File file = new File(fileLocation.getStringValue());

        try
        {
            Desktop.getDesktop().open(file);
        }
        catch (IOException e)
        {
            throw new MinorException("Unable to open file : "+fileLocation.getStringValue()+"\n" +
                    "Reason:\n"+e.getMessage());
        }
    }
}
