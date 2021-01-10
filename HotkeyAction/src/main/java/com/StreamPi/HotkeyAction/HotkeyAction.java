package com.StreamPi.HotkeyAction;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Exception.MinorException;
import com.StreamPi.Util.Version.Version;
import static java.awt.event.KeyEvent.*;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;

public class HotkeyAction extends NormalAction {

    public HotkeyAction()
    {
        setName("Hotkey");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("far-keyboard");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void initProperties() throws Exception {
        Property keyCombination = new Property("key_comb", Type.STRING);
        keyCombination.setDisplayName("Key combination (Separate using comma)");
        keyCombination.setCanBeBlank(false);

        addClientProperties(keyCombination);
    }


    private Robot robot;

    @Override
    public void initAction() throws Exception {
        robot = new Robot();
    }

    @Override
    public void onActionClicked() throws Exception {
        Property keyCombination = getClientProperties().getSingleProperty("key_comb");

        press(keyCombination.getStringValue().toUpperCase().split(","));
    }

    public void press(String[] characters) {

        ArrayList<Integer> pressedChars = new ArrayList<>();

        try{
            for (String s : characters) {
                int vkValue = getVKValue(s.trim());
                press(vkValue);
                pressedChars.add(vkValue);
            }
        }
        finally
        {
            releaseCharacters(pressedChars);
        }
    }

    private void releaseCharacters(ArrayList<Integer> chars)
    {
        for(Integer c : chars)
        {
            release(c);
        }
    }

    public int getVKValue(String character) {
        switch (character) {
            case "A": return VK_A; 
            case "B": return VK_B; 
            case "C": return VK_C; 
            case "D": return VK_D; 
            case "E": return VK_E; 
            case "F": return VK_F; 
            case "G": return VK_G; 
            case "H": return VK_H; 
            case "I": return VK_I; 
            case "J": return VK_J; 
            case "K": return VK_K; 
            case "L": return VK_L; 
            case "M": return VK_M; 
            case "N": return VK_N; 
            case "O": return VK_O; 
            case "P": return VK_P; 
            case "Q": return VK_Q; 
            case "R": return VK_R; 
            case "S": return VK_S; 
            case "T": return VK_T; 
            case "U": return VK_U; 
            case "V": return VK_V; 
            case "W": return VK_W; 
            case "X": return VK_X; 
            case "Y": return VK_Y; 
            case "Z": return VK_Z; 
            case "`": return VK_BACK_QUOTE; 
            case "0": return VK_0; 
            case "1": return VK_1; 
            case "2": return VK_2; 
            case "3": return VK_3; 
            case "4": return VK_4; 
            case "5": return VK_5; 
            case "6": return VK_6; 
            case "7": return VK_7; 
            case "8": return VK_8; 
            case "9": return VK_9; 
            case "-": return VK_MINUS; 
            case "=": return VK_EQUALS; 
            case "~": return VK_BACK_QUOTE; 
            case "!": return VK_EXCLAMATION_MARK; 
            case "@": return VK_AT; 
            case "#": return VK_NUMBER_SIGN; 
            case "$": return VK_DOLLAR; 
            case "%": return VK_5; 
            case "^": return VK_CIRCUMFLEX; 
            case "&": return VK_AMPERSAND; 
            case "*": return VK_ASTERISK; 
            case "(": return VK_LEFT_PARENTHESIS; 
            case ")": return VK_RIGHT_PARENTHESIS; 
            case "_": return VK_UNDERSCORE; 
            case "+": return VK_PLUS; 
            case "TAB": return VK_TAB;
            case "[": return VK_OPEN_BRACKET; 
            case "]": return VK_CLOSE_BRACKET; 
            case "\\": return VK_BACK_SLASH; 
            case "{": return VK_OPEN_BRACKET; 
            case "}": return VK_CLOSE_BRACKET; 
            case "|": return VK_BACK_SLASH; 
            case ";": return VK_SEMICOLON; 
            case ":": return VK_COLON; 
            case "\"": return VK_QUOTE;
            case ",": return VK_COMMA; 
            case "<": return VK_COMMA;
            case ".": return VK_PERIOD; 
            case ">": return VK_PERIOD;
            case "/": return VK_SLASH; 
            case "?": return VK_SLASH; 
            case " ": return VK_SPACE;

            case "WIN":
            case "WINDOWS":
            case "SUPER":
            case "COMMAND":
                return VK_WINDOWS;

            case "META":
                return VK_META;    

            case "SHIFT":
                return VK_SHIFT;

            case "ALT": return VK_ALT;

            case "F1": return VK_F1;
            case "F2": return VK_F2;
            case "F3": return VK_F3;
            case "F4": return VK_F4;
            case "F5": return VK_F5;
            case "F6": return VK_F6;
            case "F7": return VK_F7;
            case "F8": return VK_F8;
            case "F9": return VK_F9;
            case "F10": return VK_F10;
            case "F11": return VK_F11;
            case "F12": return VK_F12;
            case "F13": return VK_F13;
            case "F14": return VK_F14;
            case "F15": return VK_F15;
            case "F16": return VK_F16;
            case "F17": return VK_F17;
            case "F18": return VK_F18;
            case "F19": return VK_F19;
            case "F20": return VK_F20;
            case "F21": return VK_F21;
            case "F22": return VK_F22;
            case "F23": return VK_F23;
            case "F24": return VK_F24;

            case "NUMPAD 0": return VK_NUMPAD0;
            case "NUMPAD 1": return VK_NUMPAD1;
            case "NUMPAD 2": return VK_NUMPAD2;
            case "NUMPAD 3": return VK_NUMPAD3;
            case "NUMPAD 4": return VK_NUMPAD4;
            case "NUMPAD 5": return VK_NUMPAD5;
            case "NUMPAD 6": return VK_NUMPAD6;
            case "NUMPAD 7": return VK_NUMPAD7;
            case "NUMPAD 8": return VK_NUMPAD8;
            case "NUMPAD 9": return VK_NUMPAD9;

            case "NUMBER SIGN": return VK_NUMBER_SIGN;
            

            case "HOME": return VK_HOME;

            case "INSERT": return VK_INSERT;

            case "PAGE UP": return VK_PAGE_UP;
            case "PAGE DOWN": return VK_PAGE_DOWN;
            case "END": return VK_END;
            case "ENTER": return VK_ENTER;
            case "DELETE": return VK_DELETE;

            case "SCROLL LOCK": return VK_SCROLL_LOCK;
            case "PRINT SCREEN": return VK_PRINTSCREEN;
            case "PAUSE": return VK_PAUSE;

            case "NUM LOCK": return VK_NUM_LOCK;

            case "CONTROL": return VK_CONTROL;

            case "CAPS LOCK": return VK_CAPS_LOCK;

            case "NUM UP": return VK_KP_UP;
            case "UP": return VK_UP;

            case "NUM LEFT": return VK_KP_LEFT;
            case "LEFT": return VK_LEFT;

            case "ALT GRAPH": return VK_ALT_GRAPH;

            case "NUM RIGHT": return VK_KP_RIGHT;
            case "RIGHT": return VK_RIGHT;

            case "NUM DOWN": return VK_KP_DOWN;
            case "DOWN": return VK_DOWN;

            default:
                throw new IllegalArgumentException("Cannot press character " + character);
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

