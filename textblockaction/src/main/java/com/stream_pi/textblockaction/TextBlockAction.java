package com.stream_pi.textblockaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.version.Version;
import static java.awt.event.KeyEvent.*;

import java.awt.*;

public class TextBlockAction extends NormalAction {

    public TextBlockAction()
    {
        setName("Text Block");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-keyboard");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void initProperties() throws Exception {
        Property textBlockProperty = new Property("text_block", Type.STRING);
        textBlockProperty.setDisplayName("Text");
        textBlockProperty.setDefaultValueStr("Stream-Pi FTW");
        textBlockProperty.setCanBeBlank(false);

        addClientProperties(textBlockProperty);
    }


    private Robot robot;

    @Override
    public void initAction() throws Exception {
        robot = new Robot();
    }

    @Override
    public void onActionClicked() throws Exception
    {
        Property textBlockProperty = getClientProperties().getSingleProperty("text_block");

        type(textBlockProperty.getStringValue());
    }

    public void type(String toType) throws Exception
    {
        release(VK_CAPS_LOCK);
        release(VK_SHIFT);

        for (int i = 0;i<toType.length();i ++)
        {
            char c = toType.charAt(i);
        
            pressAndReleaseKey(c);
        }

        if(isShiftOn)
        {
            isShiftOn = false;
            release(VK_SHIFT);
        }
    }

    private boolean isShiftOn = false;
    public void pressAndReleaseKey(char c) throws Exception
    {
        if(Character.isUpperCase(c))
        {
            if(!isShiftOn)
            {
                isShiftOn = true;
                press(VK_SHIFT);
            }
        }
        else
        {
            if(isShiftOn)
            {
                isShiftOn = false;
                release(VK_SHIFT);
            }
        }


        switch (Character.toLowerCase(c)) {
            case 'a': pressAndRelease(VK_A); break;
            case 'b': pressAndRelease(VK_B); break; 
            case 'c': pressAndRelease(VK_C); break; 
            case 'd': pressAndRelease(VK_D); break; 
            case 'e': pressAndRelease(VK_E); break;  
            case 'f': pressAndRelease(VK_F); break; 
            case 'g': pressAndRelease(VK_G); break; 
            case 'h': pressAndRelease(VK_H); break; 
            case 'i': pressAndRelease(VK_I); break; 
            case 'j': pressAndRelease(VK_J); break; 
            case 'k': pressAndRelease(VK_K); break; 
            case 'l': pressAndRelease(VK_L); break;  
            case 'm': pressAndRelease(VK_M); break; 
            case 'n': pressAndRelease(VK_N); break; 
            case 'o': pressAndRelease(VK_O); break; 
            case 'p': pressAndRelease(VK_P); break; 
            case 'q': pressAndRelease(VK_Q); break; 
            case 'r': pressAndRelease(VK_R); break; 
            case 's': pressAndRelease(VK_S); break; 
            case 't': pressAndRelease(VK_T); break;  
            case 'u': pressAndRelease(VK_U); break;  
            case 'v': pressAndRelease(VK_V); break;  
            case 'w': pressAndRelease(VK_W); break; 
            case 'x': pressAndRelease(VK_X); break; 
            case 'y': pressAndRelease(VK_Y); break; 
            case 'z': pressAndRelease(VK_Z); break; 
            case '0': pressAndRelease(VK_0); break; 
            case '1': pressAndRelease(VK_1); break; 
            case '2': pressAndRelease(VK_2); break;  
            case '3': pressAndRelease(VK_3); break; 
            case '4': pressAndRelease(VK_4); break; 
            case '5': pressAndRelease(VK_5); break; 
            case '6': pressAndRelease(VK_6); break; 
            case '7': pressAndRelease(VK_7); break;  
            case '8': pressAndRelease(VK_8); break; 
            case '9': pressAndRelease(VK_9); break;  
            case '-': pressAndRelease(VK_MINUS); break; 
          
            case '=': pressAndRelease(VK_EQUALS); break;  
            case '`': pressAndRelease(VK_BACK_QUOTE); break;  
          
            case '[': pressAndRelease(VK_OPEN_BRACKET); break; 
            case ']': pressAndRelease(VK_CLOSE_BRACKET); break; 
            case '\\': pressAndRelease(VK_BACK_SLASH); break; 
            case '{': pressAndRelease(VK_BRACELEFT); break; 
            case '}': pressAndRelease(VK_BRACERIGHT); break; 
            case '/': pressAndRelease(VK_SLASH); break; 
            case ':': pressAndRelease(VK_SHIFT, VK_SEMICOLON); break; 
            case ';': pressAndRelease(VK_SEMICOLON); break; 
            case '\'': pressAndRelease(VK_QUOTE); break; 
            case ',': pressAndRelease(VK_COMMA); break;  
            case '<': pressAndRelease(VK_SHIFT, VK_COMMA); break;  
            case '>': pressAndRelease(VK_SHIFT, VK_PERIOD); break;
            case '.': pressAndRelease(VK_PERIOD); break; 
            case ' ': pressAndRelease(VK_SPACE); break; 


            case '~': pressAndRelease(VK_SHIFT, VK_BACK_QUOTE); break;
            case '!': pressAndRelease(VK_SHIFT, VK_1); break;
            case '@': pressAndRelease(VK_SHIFT, VK_2); break; 
            case '#': pressAndRelease(VK_SHIFT, VK_3); break; 
            case '$': pressAndRelease(VK_SHIFT, VK_4); break; 
            case '%': pressAndRelease(VK_SHIFT, VK_5); break; 
            case '^': pressAndRelease(VK_SHIFT, VK_6); break; 
            case '&': pressAndRelease(VK_SHIFT, VK_7); break; 
            case '*': pressAndRelease(VK_SHIFT, VK_8); break; 
            case '(': pressAndRelease(VK_SHIFT, VK_9); break; 
            case ')': pressAndRelease(VK_SHIFT, VK_0); break;
            case '_': pressAndRelease(VK_SHIFT, VK_MINUS); break;  
            case '+': pressAndRelease(VK_SHIFT, VK_EQUALS); break;  

            case '|': pressAndRelease(VK_SHIFT, VK_BACK_SLASH); break; 
            case '?': pressAndRelease(VK_SHIFT, VK_SLASH); break; 

            default:
                throw new IllegalArgumentException("Cannot press character " + c);
        }
        
    }

    private void pressAndRelease(int... keyCodes)
    {
        for(int key : keyCodes)
        {
            press(key);
        }

        for(int key : keyCodes)
        {
            release(key);
        }
    }
    
    private void press(int keyCode)
    {
        robot.keyPress(keyCode);
    }

    private void release(int keyCode)
    {
        robot.keyRelease(keyCode);
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}

