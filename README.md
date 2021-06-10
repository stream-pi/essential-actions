# Stream-Pi Essential Actions

Set of trusted, essential actions and integrations for Stream-Pi using the [Stream-Pi Action API](https://github.com/stream-pi/action-api). These actions are prebundled with public Stream-Pi releases.

## Prerequisites

- Java >= 11
- Maven >= 3.6.3

## Actions Help Guide

- [Hotkey](hotkeyaction/README.md)
- [Run Command](runcommandaction/README.md)
- [Twitch Chat](twitch/README.md)

## Quick Start

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/server) and copy the contents of `BuiltPlugins` to the server's
Plugins directory (`$HOME/Stream-Pi/Server/Plugins` by default), especially if you're writing your own custom action / integration.

### Linux/Mac

Build all actions by executing `make build-all` from the command line or specific actions i.e. `make twitch`.

### Windows

Build all actions by executing `build.bat` from the command line or specific actions i.e. `build.bat twitch`.

## List of Actions

Action Name           | Build Script Argument   | 
--------------------- | ----------------------- | 
 Hotkey               | `hotkey`                | 
 Play Audio Clip      | `playaudioclip`         | 
 Media Key            | `mediakey`              | 
 OBS Suite            | `obs-suite`             |
 Run Command          | `runcommand`            |
 Text Block           | `textblock`             | 
 Twitch               | `twitch-talk`           | 
 Open File            | `openfile`              | 
 Twitter              | `twitter`               | 
 Website              | `hotkey`                | 
 Run Executable       | `runexecutable`         | 
 
