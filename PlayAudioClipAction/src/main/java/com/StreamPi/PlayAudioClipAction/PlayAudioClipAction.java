package com.StreamPi.PlayAudioClipAction;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Exception.MinorException;
import com.StreamPi.Util.Version.Version;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;
import java.net.URI;

public class PlayAudioClipAction extends NormalAction {

    public PlayAudioClipAction()
    {
        setName("Play Audio Clip");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-volume-up");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
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

        mediaPlayer.setOnReady(()->
        {
            mediaPlayer.play();
        });
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}
