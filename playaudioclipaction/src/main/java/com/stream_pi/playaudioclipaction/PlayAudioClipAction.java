package com.stream_pi.playaudioclipaction;

import java.util.ArrayList;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.FileExtensionFilter;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;

import java.io.File;

public class PlayAudioClipAction extends NormalAction {

    public PlayAudioClipAction()
    {
        setName("Play Audio Clip");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-volume-up");
        setHelpLink("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(2,0,0));

        loopState = new ArrayList<>();
        loopState.add("No");
        loopState.add("Yes");
    }

    private ArrayList<String> loopState;

    @Override
    public void initProperties() throws Exception
    {
        Property looped = new Property("audio_clip_loop", Type.LIST);
        looped.setListValue(loopState);
        looped.setDisplayName("Clip Looped?");
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

        addClientProperties(audioFileLocationProperty, looped);
    }

    public AudioClip mediaPlayer = null;
    public String path = null;

    @Override
    public void onActionClicked() throws Exception
    {
        Property audioFileLocationProperty = getClientProperties().getSingleProperty("audio_location");

        if (audioFileLocationProperty.getStringValue().isBlank())
        {
            new StreamPiAlert("Media Action", "No file specified", StreamPiAlertType.ERROR).show();
            return;
        }

        String state = loopState.get(getClientProperties().getSingleProperty("audio_clip_looped").getSelectedIndex());

        if(state.equals("Yes"))
        {
            mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        }
        else if(state.equals("No"))
        {
            mediaPlayer.setCycleCount(0);
        }

        if(mediaPlayer != null)
        {
            if(mediaPlayer.isPlaying())
            {
                Platform.runLater(mediaPlayer::stop);
                return;
            }
        }

        if(!audioFileLocationProperty.getStringValue().equals(path))
        {
            path = audioFileLocationProperty.getStringValue();
            mediaPlayer = new AudioClip(new File(path).toURI().toString());
        }

        Platform.runLater(mediaPlayer::play);
    }

    @Override
    public void onShutDown()
    {
        shutDown();
    }
    @Override
    public void onActionDeleted()
    {
        shutDown();
    }

    @Override
    public void onClientDisconnected()
    {
        shutDown();
    }

    private void shutDown()
    {
        if(mediaPlayer != null)
        {
            if(mediaPlayer.isPlaying())
                Platform.runLater(mediaPlayer::stop);
        }
    }


}
