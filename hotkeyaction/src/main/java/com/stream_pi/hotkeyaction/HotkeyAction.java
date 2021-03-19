package com.stream_pi.hotkeyaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.normalaction.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

import java.util.ArrayList;

import static javafx.scene.input.KeyCode.*;

public class HotkeyAction extends NormalAction {

    public HotkeyAction()
    {
        setName("Hotkey");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("far-keyboard");
        setHelpLink("https://github.com/stream-pi/essentialactions/blob/master/hotkeyaction/README.md");
        setVersion(new Version(1,0,1));
    }

    @Override
    public void initProperties() throws Exception {
        Property keyCombination = new Property("key_comb", Type.STRING);
        keyCombination.setDisplayName("Key combination (Separate using comma)");

        addClientProperties(keyCombination);
    }


    private Robot robot;

    @Override
    public void initAction() throws Exception {
        Platform.runLater(()->robot = new Robot());
    }

    @Override
    public void onActionClicked() throws Exception
    {
        Property keyCombination = getClientProperties().getSingleProperty("key_comb");

        if(keyCombination.getStringValue().isBlank())
        {
            throw new MinorException("No key specified");
        }

        press(keyCombination.getStringValue()
            .toUpperCase()
            .replace("?","SHIFT,/")
            .replace("|","SHIFT,\\")
            .split(",")
        );
    }

    public void press(String[] characters) throws InterruptedException {

        ArrayList<KeyCode> pressedChars = new ArrayList<>();

        try{
            for (String s : characters) {
                KeyCode vkValue = getKeyCodeValue(s.trim());
                press(vkValue);
                Thread.sleep(50);
                pressedChars.add(vkValue);
            }
        }
        finally
        {
            releaseCharacters(pressedChars);
        }
    }

    private void releaseCharacters(ArrayList<KeyCode> chars) throws InterruptedException
    {
        for(KeyCode c : chars)
        {
            release(c);
            Thread.sleep(50);
        }
    }

    public KeyCode getKeyCodeValue(String character) throws IllegalArgumentException
    {
        switch (character) {
            case "A": return A;
            case "B": return B; 
            case "C": return C; 
            case "D": return D; 
            case "E": return E; 
            case "F": return F; 
            case "G": return G; 
            case "H": return H; 
            case "I": return I; 
            case "J": return J; 
            case "K": return K; 
            case "L": return L; 
            case "M": return M; 
            case "N": return N; 
            case "O": return O; 
            case "P": return P; 
            case "Q": return Q; 
            case "R": return R; 
            case "S": return S; 
            case "T": return T; 
            case "U": return U; 
            case "V": return V; 
            case "W": return W; 
            case "X": return X; 
            case "Y": return Y; 
            case "Z": return Z; 
            case "`": return BACK_QUOTE; 
            case "0": return DIGIT0;
            case "1": return DIGIT1;
            case "2": return DIGIT2;
            case "3": return DIGIT3;
            case "4": return DIGIT4;
            case "5": return DIGIT5;
            case "6": return DIGIT6;
            case "7": return DIGIT7;
            case "8": return DIGIT8;
            case "9": return DIGIT9;
            case "-": return MINUS; 
            case "=": return EQUALS; 
            case "~": return BACK_QUOTE; 
            case "!": return EXCLAMATION_MARK; 
            case "@": return AT; 
            case "#": return NUMBER_SIGN; 
            case "$": return DOLLAR;
            case "^": return CIRCUMFLEX; 
            case "&": return AMPERSAND; 
            case "*": return ASTERISK; 
            case "(": return LEFT_PARENTHESIS; 
            case ")": return RIGHT_PARENTHESIS; 
            case "_": return UNDERSCORE; 
            case "+": return PLUS; 
            case "TAB": return TAB;
            case "[": return OPEN_BRACKET; 
            case "]": return CLOSE_BRACKET; 
            case "{": return OPEN_BRACKET; 
            case "}": return CLOSE_BRACKET;
            case ";": return SEMICOLON; 
            case ":": return COLON; 
            case "\\": return BACK_SLASH; 
            case "\"": return QUOTE;
            case ",": return COMMA; 
            case "<": return LESS;
            case ".": return PERIOD; 
            case ">": return GREATER;
            case "/": return SLASH; 
            case " ": return SPACE;

            case "WIN":
            case "WINDOWS":
            case "SUPER":
            case "COMMAND":
                return WINDOWS;

            case "META":
                return META;    

            case "SHIFT":
                return SHIFT;

            case "ALT": return ALT;

            case "F1": return F1;
            case "F2": return F2;
            case "F3": return F3;
            case "F4": return F4;
            case "F5": return F5;
            case "F6": return F6;
            case "F7": return F7;
            case "F8": return F8;
            case "F9": return F9;
            case "F10": return F10;
            case "F11": return F11;
            case "F12": return F12;
            case "F13": return F13;
            case "F14": return F14;
            case "F15": return F15;
            case "F16": return F16;
            case "F17": return F17;
            case "F18": return F18;
            case "F19": return F19;
            case "F20": return F20;
            case "F21": return F21;
            case "F22": return F22;
            case "F23": return F23;
            case "F24": return F24;

            case "NUMPAD 0": return NUMPAD0;
            case "NUMPAD 1": return NUMPAD1;
            case "NUMPAD 2": return NUMPAD2;
            case "NUMPAD 3": return NUMPAD3;
            case "NUMPAD 4": return NUMPAD4;
            case "NUMPAD 5": return NUMPAD5;
            case "NUMPAD 6": return NUMPAD6;
            case "NUMPAD 7": return NUMPAD7;
            case "NUMPAD 8": return NUMPAD8;
            case "NUMPAD 9": return NUMPAD9;

            case "NUMBER SIGN": return NUMBER_SIGN;
            

            case "HOME": return HOME;

            case "INSERT": return INSERT;

            case "PAGE UP": return PAGE_UP;
            case "PAGE DOWN": return PAGE_DOWN;
            case "END": return END;
            case "ENTER": return ENTER;
            case "DELETE": return DELETE;

            case "SCROLL LOCK": return SCROLL_LOCK;
            case "PRINT SCREEN": return PRINTSCREEN;
            case "PAUSE": return PAUSE;

            case "NUM LOCK": return NUM_LOCK;

            case "CONTROL": return CONTROL;

            case "CAPS LOCK": return CAPS;
                
            case "ESCAPE":
            case "ESC":
                return ESCAPE;

            case "NUM UP": return KP_UP;
            case "UP": return UP;

            case "NUM LEFT": return KP_LEFT;
            case "LEFT": return LEFT;

            case "ALT GRAPH": return ALT_GRAPH;

            case "NUM RIGHT": return KP_RIGHT;
            case "RIGHT": return RIGHT;

            case "NUM DOWN": return KP_DOWN;
            case "DOWN": return DOWN;

            default:
                throw new IllegalArgumentException("Cannot press character " + character);
        }
    }

    
    private void press(KeyCode keyCode)
    {
        Platform.runLater(()->robot.keyPress(keyCode));
    }

    private void release(KeyCode keyCode)
    {
        Platform.runLater(()->robot.keyRelease(keyCode));
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}

