package com.stream_pi.textblockaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import javafx.application.Platform;
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
        setVersion(new Version(2,0,0));
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

    @Override
    public void initAction()
    {
        if (robot == null)
        {
            Platform.runLater(()->robot = new Robot());
        }
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        String textBlock = getClientProperties().getSingleProperty("text_block").getStringValue();

        System.out.println(textBlock);
        Platform.runLater(()->
        {

            robot.keyRelease(KeyCode.CAPS);
            robot.keyRelease(KeyCode.SHIFT);

            for (int i = 0;i<textBlock.length();i ++)
            {
                char c = textBlock.charAt(i);

                pressAndReleaseKey(c);
            }

            if(isShiftOn)
            {
                isShiftOn = false;
                robot.keyRelease(KeyCode.SHIFT);
            }
        });

    }

    private boolean isShiftOn = false;
    public void pressAndReleaseKey(char c)
    {
        if(Character.isUpperCase(c))
        {
            if(!isShiftOn)
            {
                isShiftOn = true;
                robot.keyPress(KeyCode.SHIFT);
            }
        }
        else
        {
            if(isShiftOn)
            {
                isShiftOn = false;
                robot.keyRelease(KeyCode.SHIFT);
            }
        }


        switch (Character.toLowerCase(c))
        {
            case 'a': pressAndRelease(KeyCode.A); break;
            case 'b': pressAndRelease(KeyCode.B); break;
            case 'c': pressAndRelease(KeyCode.C); break;
            case 'd': pressAndRelease(KeyCode.D); break;
            case 'e': pressAndRelease(KeyCode.E); break;
            case 'f': pressAndRelease(KeyCode.F); break;
            case 'g': pressAndRelease(KeyCode.G); break;
            case 'h': pressAndRelease(KeyCode.H); break;
            case 'i': pressAndRelease(KeyCode.I); break;
            case 'j': pressAndRelease(KeyCode.J); break;
            case 'k': pressAndRelease(KeyCode.K); break;
            case 'l': pressAndRelease(KeyCode.L); break;
            case 'm': pressAndRelease(KeyCode.M); break;
            case 'n': pressAndRelease(KeyCode.N); break;
            case 'o': pressAndRelease(KeyCode.O); break;
            case 'p': pressAndRelease(KeyCode.P); break;
            case 'q': pressAndRelease(KeyCode.Q); break;
            case 'r': pressAndRelease(KeyCode.R); break;
            case 's': pressAndRelease(KeyCode.S); break;
            case 't': pressAndRelease(KeyCode.T); break;
            case 'u': pressAndRelease(KeyCode.U); break;
            case 'v': pressAndRelease(KeyCode.V); break;
            case 'w': pressAndRelease(KeyCode.W); break;
            case 'x': pressAndRelease(KeyCode.X); break;
            case 'y': pressAndRelease(KeyCode.Y); break;
            case 'z': pressAndRelease(KeyCode.Z); break;
            case '0': pressAndRelease(KeyCode.DIGIT0); break;
            case '1': pressAndRelease(KeyCode.DIGIT1); break;
            case '2': pressAndRelease(KeyCode.DIGIT2); break;
            case '3': pressAndRelease(KeyCode.DIGIT3); break;
            case '4': pressAndRelease(KeyCode.DIGIT4); break;
            case '5': pressAndRelease(KeyCode.DIGIT5); break;
            case '6': pressAndRelease(KeyCode.DIGIT6); break;
            case '7': pressAndRelease(KeyCode.DIGIT7); break;
            case '8': pressAndRelease(KeyCode.DIGIT8); break;
            case '9': pressAndRelease(KeyCode.DIGIT9); break;
            case '-': pressAndRelease(KeyCode.MINUS); break;

            case '=': pressAndRelease(KeyCode.EQUALS); break;
            case '`': pressAndRelease(KeyCode.BACK_QUOTE); break;

            case '[': pressAndRelease(KeyCode.OPEN_BRACKET); break;
            case ']': pressAndRelease(KeyCode.CLOSE_BRACKET); break;
            case '\\': pressAndRelease(KeyCode.BACK_SLASH); break;

            case '/': pressAndRelease(KeyCode.SLASH); break;
            case ';': pressAndRelease(KeyCode.SEMICOLON); break;
            case '\'': pressAndRelease(KeyCode.BACK_SLASH); break;
            case ',': pressAndRelease(KeyCode.COMMA); break;
            case '<': pressAndRelease(KeyCode.LESS); break;
            case '>': pressAndRelease(KeyCode.GREATER); break;
            case '.': pressAndRelease(KeyCode.PERIOD); break;
            case ' ': pressAndRelease(KeyCode.SPACE); break;


            case ':': pressAndRelease(KeyCode.COLON); break;
            case '~': pressAndRelease(KeyCode.SHIFT, KeyCode.BACK_QUOTE); break;
            case '!': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT1); break;
            case '@': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT2); break;
            case '#': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT3); break;
            case '$': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT4); break;
            case '%': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT5); break;
            case '^': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT6); break;
            case '&': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT7); break;
            case '*': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT8); break;
            case '(': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT9); break;
            case ')': pressAndRelease(KeyCode.SHIFT, KeyCode.DIGIT0); break;
            case '_': pressAndRelease(KeyCode.SHIFT, KeyCode.MINUS); break;
            case '+': pressAndRelease(KeyCode.SHIFT, KeyCode.EQUALS); break;
            case '{': pressAndRelease(KeyCode.SHIFT, KeyCode.OPEN_BRACKET); break;
            case '}': pressAndRelease(KeyCode.SHIFT, KeyCode.CLOSE_BRACKET); break;

            case '|': pressAndRelease(KeyCode.SHIFT, KeyCode.BACK_SLASH); break;
            case '?': pressAndRelease(KeyCode.SHIFT, KeyCode.SLASH); break;

            default:
                throw new IllegalArgumentException("Cannot press character " + c);
        }
    }

    private void pressAndRelease(KeyCode... keyCodes)
    {
        for(KeyCode key : keyCodes)
        {
            robot.keyPress(key);
        }

        for(KeyCode key : keyCodes)
        {
            robot.keyRelease(key);
        }
    }
}

