package com.stream_pi.textblockaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

public class TextBlockAction extends NormalAction
{
    public TextBlockAction()
    {
        setName("Text Block");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-keyboard");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(3,0,0));
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property textBlockProperty = new Property("text_block", Type.STRING);
        textBlockProperty.setDisplayName("Text");
        textBlockProperty.setDefaultValueStr("Stream-Pi FTW");
        textBlockProperty.setCanBeBlank(false);

        addClientProperties(textBlockProperty);
    }

    private static Robot robot;
    private static Clipboard clipboard;

    @Override
    public void initAction()
    {
        if (robot == null)
        {
            Platform.runLater(()->{
                robot = new Robot();
                clipboard = Clipboard.getSystemClipboard();
            });
        }
    }

    @Override
    public void onActionClicked()
    {
        Platform.runLater(()->{
            try
            {
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(getClientProperties().getSingleProperty("text_block").getStringValue());
                clipboard.setContent(clipboardContent);

                robot.keyPress(KeyCode.CONTROL);
                robot.keyPress(KeyCode.V);
                robot.keyRelease(KeyCode.V);
                robot.keyRelease(KeyCode.CONTROL);
            }
            catch (Exception e)
            {
                throwMinorException(new MinorException(e.getMessage()));
            }
        });
    }
}

