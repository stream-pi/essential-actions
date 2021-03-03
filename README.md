# Stream-Pi Essential Actions

Set of trusted, pre-bundled actions and integrations for Stream-Pi using the [Stream-Pi Action API](https://github.com/stream-pi/actionapi).

## Prerequisites

- Java >= 11
- Maven >= 3.6.3

## List of Actions

- Hotkey
- Website
- Twitter
- OBS Actions
- Run Command
- Text Block
- Media File
- Media Key
- Twitch

## Actions Help Guide

### Twitch Chat Integration

The first step is to acquire an OAuth token from https://twitchapps.com/tmi/, the generated OAuth token should look something like `oauth:xxxxx`.

In the Stream-Pi Server's Plugin page enter your Twitch username, and the generated token then click on `Save Twitch chat credentials` button. You should then be able to use the pre-bundled Twitch chat actions. 

### Supported actions

- Send channel message

### Running locally

Copy the `twitch-chat-connect`, `twitch-send-channel-msg`, and `Java-Twirk` jar files from the `PreBuiltPlugins` directory to your Stream-Pi server plugins' directory. 

---

## Quick Start

Build all actions by executing `make build-all` from the command line or specific actions i.e. `make build-twitch-chat-action`, see [Makefile](Makefile) for complete list.

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/server) and copy the contents of `PreBuiltPlugins/` to the server's
Plugins directory (`data/Plugins` by default), especially if you're writing your own custom action / integration.
