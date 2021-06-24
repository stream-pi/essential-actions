# Twitch Chat Integration

The first step is to acquire an [OAuth token](https://twitchapps.com/tmi/), the generated OAuth token should look something like `oauth:xxxxx`.

Then in the Stream-Pi Server's Plugin page you will need to enter your Twitch username with the generated OAuth token then click on `Save Twitch Chat credentials` button.
You should then be able to use the pre-bundled Twitch chat actions.

## Supported actions (see [Twitch Chat Commands](https://help.twitch.tv/s/article/chat-commands?language=en_US) for full documentation)

### All Users

- Set username color
    - Normal users can choose between Blue, Coral, DodgerBlue, SpringGreen, YellowGreen, Green, OrangeRed, Red, GoldenRod, HotPink, CadetBlue, SeaGreen, Chocolate, BlueViolet, and Firebrick. Twitch Turbo users can use any Hex value (i.e: #000000).
- Send channel message
- Whisper (send user message)

### Broadcaster and Mods

- Clear chat

### Broadcaster and channel editors

- Run commercial
- Host
- Unhost
- Raid
- Unraid
- Add stream marker
- Toggle slow mode
- Toggle subs-only mode
- Toggle followers-only mode

## Running locally

Copy the `twitch-api-java` from the `Dependencies` directory and the `twitch-xxx-action` jar files from the `PreBuiltPlugins` directory to your Stream-Pi server plugins' directory.