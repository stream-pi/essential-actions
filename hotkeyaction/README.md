# Hotkey Action plugin

This plugin allows map and execute keyboard shortcuts

## KeyBoard Mapping Reference to Hotkeyaction

The following table shows the relation between our keyboard and what we have to type on the plugin field to fire the hotkeys we want:

Hotkey mapping plugin | Keyboard Key | Key Value
--------------------- | ------------ | ---------
 A                    | A            | A
 B                    | B            | B  
 C                    | C            | C 
 D                    | D            | D 
 E                    | E            | E 
 F                    | F            | F 
 G                    | G            | G 
 H                    | H            | H 
 I                    | I            | I 
 J                    | J            | J 
 K                    | K            | K 
 L                    | L            | L 
 M                    | M            | M 
 N                    | N            | N 
 O                    | O            | O 
 P                    | P            | P 
 Q                    | Q            | Q 
 R                    | R            | R 
 S                    | S            | S 
 T                    | T            | T 
 U                    | U            | U 
 V                    | V            | V 
 W                    | W            | W 
 X                    | X            | X 
 Y                    | Y            | Y 
 Z                    | Z            | Z 
 `                    | `            | BACK_QUOTE 
 0                    | 0            | DIGIT0
 1                    | 1            | DIGIT1
 2                    | 2            | DIGIT2
 3                    | 3            | DIGIT3
 4                    | 4            | DIGIT4
 5                    | 5            | DIGIT5
 6                    | 6            | DIGIT6
 7                    | 7            | DIGIT7
 8                    | 8            | DIGIT8
 9                    | 9            | DIGIT9
 \-                    | -            | MINUS 
 =                    | =            | EQUALS 
 ~                    | ~            | BACK_QUOTE 
 !                    | !            | EXCLAMATION_MARK 
 @                    | @            | AT 
 \#                    | #            | NUMBER_SIGN 
 $                    | $            | DOLLAR
 ^                    | ^            | CIRCUMFLEX 
 &                    | &            | AMPERSAND 
 \*                    | *            | ASTERISK 
 (                    | (            | LEFT_PARENTHESIS 
 )                    | )            | RIGHT_PARENTHESIS 
 \_                    | _            | UNDERSCORE 
 \+                    | +            | PLUS 
 TAB                  | TAB           | TAB
 [                    | [            | OPEN_BRACKET 
 ]                    | ]            | CLOSE_BRACKET 
 \\                   | \\           | BACK_SLASH 
 {                    | {            | OPEN_BRACKET 
 }                    | }            | CLOSE_BRACKET 
 \|                   | \|            | PIPE 
 \;                   | ;            | SEMICOLON 
 \:                   | :            | COLON 
 \                    | \            | BACK_SLASH
 ,                    | ,            | COMMA 
 \<                   | <            | LESS
 \.                   | .            | PERIOD 
 \>                   | >            | GREATER
 /                    | /            | SLASH 
" "                   | " "          | SPACE
WIN\|WINDOWS\|SUPER\|COMMAND    | WINDOWS   | WINDOWS
META                  | `dont know`  | `dont know` 
SHIFT                 |    SHIFT    | SHIFT
ALT                   | ALT          | ALT
F1                    | F1           | F1
F2                    | F2           | F2
F3                    | F3           | F3
F4                    | F4           | F4
F5                    | F5           | F5
F6                    | F6           | F6
F7                    | F7           | F7
F8                    | F8           | F8
F9                    | F9           | F9
F10                   | F10          | F10
F11                   | F11          | F11
F12                   | F12          | F12
F13                   | F13          | F13
F14                   | F14          | F14
F15                   | F15          | F15
F16                   | F16          | F16
F17                   | F17          | F17
F18                   | F18          | F18
F19                   | F19          | F19
F20                   | F20          | F20
F21                   | F21          | F21
F22                   | F22          | F22
F23                   | F23          | F23
F24                   | F24          | F24
NUMPAD 0              | NUMPAD 0     | NUMPAD0
NUMPAD 1              | NUMPAD 1     | NUMPAD1
NUMPAD 2              | NUMPAD 2     | NUMPAD2
NUMPAD 3              | NUMPAD 3     | NUMPAD3
NUMPAD 4              | NUMPAD 4     | NUMPAD4
NUMPAD 5              | NUMPAD 5     | NUMPAD5
NUMPAD 6              | NUMPAD 6     | NUMPAD6
NUMPAD 7              | NUMPAD 7     | NUMPAD7
NUMPAD 8              | NUMPAD 8     | NUMPAD8
NUMPAD 9              | NUMPAD 9     | NUMPAD9
NUMBER SIGN           | NUMBER SIGN  | NUMBER_SIGN
HOME                  | HOME         | HOME
INSERT                | INSERT       | INSERT
PAGE UP               | PAGE UP      | PAGE_UP
PAGE DOWN             | PAGE DOWN    | PAGE_DOWN
END                   | END          | END
ENTER                 | ENTER        | ENTER
DELETE                | DELETE       | DELETE
SCROLL LOCK           | SCROLL LOCK  | SCROLL_LOCK
PRINT SCREEN          | PRINT SCREEN | PRINTSCREEN
PAUSE                 | PAUSE        | PAUSE
NUM LOCK              | NUM LOCK     | NUM_LOCK
CONTROL               | CONTROL      | CONTROL
CAPS LOCK             | CAPS LOCK    | CAPS
NUM UP                | NUM UP       | KP_UP
UP                    | UP           | UP
NUM LEFT              | NUM LEFT     | KP_LEFT
LEFT                  | LEFT         | LEFT
ALT GRAPH             | ALT GRAPH    | ALT_GRAPH
NUM RIGHT             | NUM RIGHT    | KP_RIGHT
RIGHT                 | RIGHT        | RIGHT
NUM DOWN              | NUM DOWN     | KP_DOWN
DOWN                  | DOWN         | DOWN;

## Usage

To create a new hotkey, just drag the hotkey feature to the desired button and in the field `Key combination (separate using comma)` just type your combination. 

Example: in ubuntu 20.04 for opening terminal just type `CONTROL,ALT,T` 


## Known bugs: 
- Windows key in Linux distros is not working for any keymap. 