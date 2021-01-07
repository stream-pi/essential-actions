package com.StreamPi.MediaKeyAction;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Alert.StreamPiAlert;
import com.StreamPi.Util.Alert.StreamPiAlertType;
import com.StreamPi.Util.Exception.MinorException;
import com.StreamPi.Util.Platform.Platform;
import com.StreamPi.Util.Version.Version;

import java.io.File;
import java.net.URI;
import java.util.List;

public class MediaKeyAction extends NormalAction {

    public MediaKeyAction()
    {
        setName("Media Key Action");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-volume-up");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));
    }

    private List<String> states;

    @Override
    public void initProperties() throws Exception {
        states = List.of("Play/Pause", "Previous", "Next", "Vol Up", "Vol Down", "Mute", "Stop");

        Property mediaKeyTypeProperty = new Property("media_key_type", Type.LIST);
        mediaKeyTypeProperty.setListValue(states);
        mediaKeyTypeProperty.setDisplayName("Media Key");

        addClientProperties(mediaKeyTypeProperty);
    }

    @Override
    public void initAction() throws Exception {
    }

    @Override
    public void onActionClicked() throws Exception 
    {
        int state = getClientProperties().getSingleProperty("media_key_type").getSelectedIndex();

        switch(getServerConnection().getPlatform())
        {
            case LINUX : 
            case LINUX_RPI :
                linuxHandler(state);
                break;
            
            default:
                othersHandler();    
        }
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }

    private int runCommand(String command) throws Exception
    {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command);
        return pr.waitFor();
    }

    private void linuxHandler(int state) throws Exception
    {
        try
        {
            switch(state)
            {
                case 0: runCommand("xdotool key XF86AudioPlay"); break;
                case 1: runCommand("xdotool key XF86AudioPrev"); break;
                case 2: runCommand("xdotool key XF86AudioNext"); break;
                case 3: runCommand("xdotool key XF86AudioRaiseVolume"); break;
                case 4: runCommand("xdotool key XF86AudioLowerVolume"); break;
                case 5: runCommand("xdotool key XF86AudioMute"); break;
                case 6: runCommand("xdotool key XF86AudioStop"); break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            new StreamPiAlert("Uh Oh!",
                "It looks like 'xdotool' is not installed in this computer. Please install it and try again.",
                StreamPiAlertType.ERROR
            ).show();
        }
    }

    private void othersHandler()
    {
        new StreamPiAlert("Uh Oh!",
            "Media Keys arent supported on this platform yet. Check out the supported platforms by clicking the '?' button on plugins pane/Plugins Settings.",
            StreamPiAlertType.ERROR
        ).show();
    }
}
