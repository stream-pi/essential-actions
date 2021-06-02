package com.stream_pi.executeapplication;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.FileExtensionFilter;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

import java.awt.*;
import java.net.URI;

import java.io.File;

public class ExecuteApplication extends NormalAction
{

    public ExecuteApplication()
    {
        setName("Execute Application");
        setAuthor("quimodotcom");
        setServerButtonGraphic("fas-paste");
        setVersion(new Version(1,1,2));

        setCategory("Exclusive Actions");

    }

    @Override
    public void initProperties() throws Exception {
        Property applicationProp = new Property("app_location", Type.STRING);
        applicationProp.setControlType(ControlType.FILE_PATH);
        applicationProp.setDisplayName("Application File Location");
        applicationProp.setExtensionFilters(
                new FileExtensionFilter("EXE","*.exe"),
                new FileExtensionFilter("MSI","*.msi"),
                new FileExtensionFilter("BAT","*.bat"),
                new FileExtensionFilter("CMD","*.cmd"),
                new FileExtensionFilter("COM","*.com"),
                new FileExtensionFilter("VB","*.vb", "*.vbs"),
                new FileExtensionFilter("LNK","*.lnk")
        );

        addClientProperties(applicationProp);
    }


    @Override
    public void initAction()  {

    }

    @Override
    public void onActionClicked() throws MinorException
    {
        Property applicationProp = getClientProperties().getSingleProperty("app_location");

        if (applicationProp.getStringValue().isBlank())
        {
            throw new MinorException("No file specified");
            return;
        }

        File file = new File(applicationProp.getStringValue());

        Desktop.getDesktop().open(file);
    }
}
