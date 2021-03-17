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

The first step is to acquire an [OAuth token](https://twitchapps.com/tmi/), the generated OAuth token should look something like `oauth:xxxxx`.

Then in the Stream-Pi Server's Plugin page you will need to enter your Twitch username with the generated OAuth token then click on `Save Twitch Chat credentials` button.
You should then be able to use the pre-bundled Twitch chat actions. 

### Supported actions (see [Chat Commands](https://help.twitch.tv/s/article/chat-commands?language=en_US) for full documentation)

#### All Users

- Set username color
    - Normal users can choose between Blue, Coral, DodgerBlue, SpringGreen, YellowGreen, Green, OrangeRed, Red, GoldenRod, HotPink, CadetBlue, SeaGreen, Chocolate, BlueViolet, and Firebrick. Twitch Turbo users can use any Hex value (i.e: #000000).
- Send channel message
- Whisper (send user message)

#### Broadcaster and Mods

- Clear chat

#### Broadcaster and channel editors

- Run commercial
- Host
- Unhost
- Raid
- Unraid
- Add stream marker

#### TODO (pending [toggle-button functionality](https://github.com/stream-pi/actionapi/issues/5))

- Toggle slow mode
- Toggle followers-only mode
- Toggle subs-only mode
- Toggle emotes-only mode

#### Running locally

Copy the `Java-Twirk` and the `twitch-xxx-action` jar files from the `PreBuiltPlugins` directory to your Stream-Pi server plugins' directory. 

---

## Quick Start

Build all actions by executing `make build-all` from the command line or specific actions i.e. `make build-twitch-chat-action`, see [Makefile](Makefile) for complete list.

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/server) and copy the contents of `PreBuiltPlugins` to the server's
Plugins directory (`data/Plugins` by default), especially if you're writing your own custom action / integration.
