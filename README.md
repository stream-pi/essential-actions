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

## Quick Start

Build actions by executing `./build.sh` from the command line.

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/server) and copy the contents of `PreBuiltPlugins/` to the server's
Plugins directory (`data/Plugins` by default), especially if you're writing your own custom action / integration.
