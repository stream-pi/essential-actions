package com.stream_pi.mediakeyaction;

import com.stream_pi.action_api.actionproperty.property.ListValue;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.platform.Platform;
import com.stream_pi.util.version.Version;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class MediaKeyAction extends NormalAction
{

    public MediaKeyAction()
    {
        setName("Media Key Action");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-volume-up");
        setHelpLink("https://github.com/stream-pi/essentialactions/tree/master/mediakeyaction");
        setVersion(new Version(1, 1, 1));
    }

    @Override
    public void initProperties() throws MinorException
    {
        final List<ListValue> states = List.of(
                new ListValue("Play/Pause"),
                new ListValue("Previous"),
                new ListValue("Next"),
                new ListValue("Vol Up"),
                new ListValue("Vol Down"),
                new ListValue("Mute"),
                new ListValue("Stop"));

        Property mediaKeyTypeProperty = new Property("media_key_type", Type.LIST);
        mediaKeyTypeProperty.setListValue(states);
        mediaKeyTypeProperty.setDisplayName("Media Key");

        addClientProperties(mediaKeyTypeProperty);
    }

    @Override
    public void initAction() throws MinorException
    {
        try
        {
            if (getPlatform() == Platform.WINDOWS)
            {
                extractKeyEXEToTmpDirectory();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new MinorException("Error", "IOException occurred. \n\n" + e.getMessage());
        }

    }

    private static String absolutePathToExe;

    private void extractKeyEXEToTmpDirectory() throws IOException
    {
        String tmpDirectory = System.getProperty("java.io.tmpdir");

        InputStream is = Objects.requireNonNull(getClass().getResource("key.exe")).openStream();
        absolutePathToExe = tmpDirectory + "/key.exe";
        File file = new File(absolutePathToExe);
        file.deleteOnExit();
        OutputStream os = new FileOutputStream(file);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1)
        {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        int state = getClientProperties().getSingleProperty("media_key_type").getSelectedIndex();

        switch (getPlatform())
        {
            case LINUX:
            case MAC:
                linuxAndMacHandler(state);
                break;

            case WINDOWS:
                windowsHandler(state);
                break;

            default:
                othersHandler();
        }
    }

    private void runCommand(String command) throws Exception
    {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command);
        pr.waitFor();
    }

    private void linuxAndMacHandler(int state) throws MinorException
    {
        try
        {
            switch (state)
            {
                case 0:
                    runCommand("xdotool key XF86AudioPlay");
                    break;
                case 1:
                    runCommand("xdotool key XF86AudioPrev");
                    break;
                case 2:
                    runCommand("xdotool key XF86AudioNext");
                    break;
                case 3:
                    runCommand("xdotool key XF86AudioRaiseVolume");
                    break;
                case 4:
                    runCommand("xdotool key XF86AudioLowerVolume");
                    break;
                case 5:
                    runCommand("xdotool key XF86AudioMute");
                    break;
                case 6:
                    runCommand("xdotool key XF86AudioStop");
                    break;
            }
        } catch (Exception e)
        {
            e.printStackTrace();

            throw new MinorException(
                    "It looks like 'xdotool' is not installed in this computer. Please install it and try again."
            );
        }
    }

    private void windowsHandler(int state) throws MinorException
    {
        try
        {
            String exe = "\"" + absolutePathToExe + "\"";
            switch (state)
            {
                case 0:
                    runCommand(exe + " 0xB3");
                    break;
                case 1:
                    runCommand(exe + " 0xB1");
                    break;
                case 2:
                    runCommand(exe + " 0xB0");
                    break;
                case 3:
                    runCommand(exe + " 0xAF");
                    break;
                case 4:
                    runCommand(exe + " 0xAE");
                    break;
                case 5:
                    runCommand(exe + " 0xAD");
                    break;
                case 6:
                    runCommand(exe + " 0xB2");
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw new MinorException(
                    "Internal Error occurred. Probably mismatched architecture. Check logs, stacktrace. Report to us."
            );
        }
    }


    private void othersHandler()
    {
        throw new UnsupportedOperationException(
                "Media Keys arent supported on this platform yet. Check out the supported platforms by clicking the '?' button on Plugins Pane"
        );
    }
}
