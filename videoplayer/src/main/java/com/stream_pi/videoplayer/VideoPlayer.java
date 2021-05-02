package com.stream_pi.videoplayer;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.FileExtensionFilter;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;

import java.awt.*;
import java.net.URI;

import java.io.File;

import javafx.scene.control.Button;

public class VideoPlayer extends NormalAction
{

    public VideoPlayer()
    {
        setName("Video Player");
        setAuthor("quimo");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));

        setCategory("Exclusive Actions");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property property1 = new Property("video_url", Type.STRING);
        property1.setControlType(ControlType.FILE_PATH);
        property1.setDefaultValueStr("Video.mp4");
        property1.setDisplayName("Video File Location");
        property1.setExtensionFilters(
            new FileExtensionFilter("MP4","*.mp4"),
            new FileExtensionFilter("MOV","*.mov"),
            new FileExtensionFilter("WMV","*.wmv"),
            new FileExtensionFilter("AVI","*.avi"),
            new FileExtensionFilter("MKV","*.mkv"),
            new FileExtensionFilter("AVCHD","*.m2ts"),
            new FileExtensionFilter("WEBM","*.webm")
    );


        addClientProperties(
                property1
        );
    }


    @Override
    public void initAction()  {

    }

    @Override
    public void initClientActionSettingsButtonBar()
    {
    }

    @Override
    public void onActionClicked() throws Exception
    {
        Property propertyobj = getClientProperties().getSingleProperty("video_url");

        String path = propertyobj.getStringValue();

        File file = new File(path);

        Desktop.getDesktop().open(file);
    }
}
