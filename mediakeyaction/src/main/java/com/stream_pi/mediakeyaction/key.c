//
// key.c - Pauses, then simulates a key press
//
// Written by Ted Burke - last updated 3-9-2014
// Modified by Debayan Sutradhar (rnayabed) to use multiple keystrokes - 19th February 2021
//
// To compile with MinGW:
//
//      gcc -o keystroke.exe keystroke.c
//
// To run the program:
//
//      keystroke.exe
//
// ...then switch to e.g. a Notepad window and wait
// 5 seconds for the A key to be magically pressed.
//
 
// Because the SendInput function is only supported in
// Windows 2000 and later, WINVER needs to be set as
// follows so that SendInput gets defined when windows.h
// is included below.

#define WINVER 0x0500
#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
 
int main(int argc, char *argv[])
{
    // This structure will be used to create the keyboard
    // input event.
    INPUT ip;
 
    // Pause for 5 seconds.
    //Sleep(5000);
 
    // Set up a generic keyboard event.
    ip.type = INPUT_KEYBOARD;
    ip.ki.wScan = 0;// DirectInput key scancode for the "A" key
    ip.ki.time = 0;
    ip.ki.dwExtraInfo = 0;

    // read a hex string and turn it into a value
    //virtual key code
    int vkc;
    sscanf(argv[1], "%x", &vkc);
 
    // Press the key (using scancode to specify which key)
    ip.ki.wVk = vkc;    
    ip.ki.dwFlags = 0;
    SendInput(1, &ip, sizeof(INPUT));
 
    // Release the key (again, using scancode to specify which key)
    ip.ki.dwFlags = KEYEVENTF_KEYUP | KEYEVENTF_SCANCODE;
    SendInput(1, &ip, sizeof(INPUT));
 
    // Exit normally
    return 0;
}