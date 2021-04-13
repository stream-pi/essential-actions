package com.stream_pi.documentopen;

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

public class DocumentOpen extends NormalAction
{

    public DocumentOpen()
    {
        setName("Document Open");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));

        setCategory("Essential Actions");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property property1 = new Property("documentopen", Type.STRING);
        property1.setDefaultValueStr("Document.pdf");
        property1.setControlType(ControlType.FILE_PATH);
        property1.setDisplayName("Document File Location");
        property1.setExtensionFilters(
                new FileExtensionFilter("pdf","*.pdf"),
                new FileExtensionFilter("docx", "*.docx")
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
    public void onActionClicked() throws Exception
    {
        Property documentloc = getClientProperties().getSingleProperty("documentopen");

        if (documentloc.getStringValue().isBlank())
        {
            new StreamPiAlert("Document Open Action", "No file specified", StreamPiAlertType.ERROR).show();
            return;
        }

        File file = new File(documentloc.getStringValue());

        //Open PDF file
        Desktop.getDesktop().open(file);
    }
}
