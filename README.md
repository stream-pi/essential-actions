# Stream-Pi Essential Actions

![version](https://img.shields.io/badge/Version-1.0.0-green)

Set of pre-built actions and integrations for the Stream-Pi using the [Stream-Pi Action API](https://github.com/stream-pi/actionapi).

## Prerequisites

- Java >= 11
- Maven >= 3.6.3

## Quick Start

Build actions by executing `./build.sh` from the command line.

To test these actions out in your local environment you'll need to run the [Stream-Pi Server](https://github.com/stream-pi/actionapi) and copy the contents of `PreBuiltPlugins/` to the server's
Plugins directory (`data/Plugins` by default), especially if you're writing your own custom action / integration.