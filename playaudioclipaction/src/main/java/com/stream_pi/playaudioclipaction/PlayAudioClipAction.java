package com.stream_pi.playaudioclipaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class PlayAudioClipAction extends NormalAction {

    public PlayAudioClipAction()
    {
        setName("Play Audio Clip");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-volume-up");
        setHelpLink("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void initProperties() throws Exception {
        Property audioFileLocationProperty = new Property("audio_location", Type.STRING);
        audioFileLocationProperty.setDisplayName("Audio File Location");

        addClientProperties(audioFileLocationProperty);
    }

    @Override
    public void initAction() throws Exception {
    }

    @Override
    public void onActionClicked() throws Exception 
    {
        Property audioFileLocationProperty = getClientProperties().getSingleProperty("audio_location");

        if(audioFileLocationProperty.getStringValue().isBlank())
        {
            new StreamPiAlert("Media Action", "No file specified", StreamPiAlertType.ERROR).show();
            return;
        }

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(audioFileLocationProperty.getStringValue()).toURI().toString()));

        mediaPlayer.setOnReady(mediaPlayer::play);
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}
