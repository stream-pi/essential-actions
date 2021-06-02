package com.stream_pi.playmusicclipaction;

import java.util.ArrayList;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.FileExtensionFilter;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import java.awt.*;
import java.net.URI;

import java.io.File;

public class PlayMusicClipAction extends NormalAction {

    public PlayMusicClipAction()
    {
        setName("Play Music Clip");
        setCategory("Essentials");
        setAuthor("quimodotcom");
        setServerButtonGraphic("fas-volume-up");
        setHelpLink("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void initProperties() throws Exception
    {
        Property audioFileLocationProperty = new Property("audio_location", Type.STRING);
        audioFileLocationProperty.setControlType(ControlType.FILE_PATH);
        audioFileLocationProperty.setDisplayName("Audio File Location");
        audioFileLocationProperty.setExtensionFilters(
                new FileExtensionFilter("MP3","*.mp3"),
                new FileExtensionFilter("MP4","*.mp4", "*.m4a", "*.m4v"),
                new FileExtensionFilter("WAV","*.wav"),
                new FileExtensionFilter("AIFF","*.aif", "*.aiff"),
                new FileExtensionFilter("FXM","*.fxm"),
                new FileExtensionFilter("FLV","*.flv"),
                new FileExtensionFilter("HLS","*.m3u8")
        );

        addClientProperties(audioFileLocationProperty);
    }


    @Override
    public void onActionClicked() throws MinorException
    {
        
        Property audioFileLocationProperty1 = getClientProperties().getSingleProperty("audio_location");

        File file = new File(audioFileLocationProperty1.getStringValue());

        Desktop.getDesktop().open(file);

    }
}
