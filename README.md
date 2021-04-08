# Stream-Pi Essential Actions

Set of trusted, pre-bundled actions and integrations for Stream-Pi using the [Stream-Pi Action API](https://github.com/stream-pi/actionapi).

## Prerequisites

- Java >= 11
- Maven >= 3.6.3

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
- [Twitch Chat](twitch/README.md)

---

## Quick Start

Build all actions by executing `make build-all` from the command line or specific actions i.e. `make build-twitch-chat-action`, see [Makefile](Makefile) for complete list.

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/server) and copy the contents of `PreBuiltPlugins` to the server's
Plugins directory (`data/Plugins` by default), especially if you're writing your own custom action / integration.
