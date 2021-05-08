# Stream-Pi Essential Actions

Set of trusted, pre-bundled actions and integrations for Stream-Pi using the [Stream-Pi Action API](https://github.com/stream-pi/actionapi).

## Prerequisites

- Java >= 11

## List of Actions

- Hotkey
- Media File
- Media Key
- OBS Actions
- Run Command
- Text Block
- Twitch Chat
- Twitter
- Website

## Actions Help Guide

- [Hotkey](hotkeyaction/README.md)
- [Run Command](runcommandaction/README.md)
- [Twitch Chat](twitch/README.md)

## Quick Start

### on Linux or Mac

Build all actions by executing `make build-all` from the command line or specific actions i.e. `make twitch-chat`, see [Makefile](Makefile) for complete list.

### on Windows

Build all actions by executing `build.bat` from the command line or specific actions i.e. `build.bat Hotkey`, see [batch file](build.bat) for complete list.

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/server) and copy the contents of `PreBuiltPlugins` to the server's
Plugins directory (`$HOME/Stream-Pi/Server/Plugins` by default), especially if you're writing your own custom action / integration.
