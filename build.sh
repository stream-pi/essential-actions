#!/bin/bash

FOLD=../PreBuiltPlugins

hotkey() {
  pushd . || exit
  cd hotkeyaction && mvn clean package
  mv target/hotkeyaction-1.0.0.jar $FOLD/hotkeyaction.jar
  popd || exit
}



mediakey() {
  pushd . || exit
  cd mediakeyaction && mvn clean package
  mv target/mediakeyaction-1.0.0.jar $FOLD/mediakeyaction.jar
  popd || exit
}

obssuite() {
  pushd . || exit
  cd obssuite/mother && mvn clean install package
  mv target/obssuite_motheraction-1.0.0.jar ../$FOLD/obssuite_motheraction.jar

  cd ../setcurrentprofile && mvn clean package
  mv target/obssuite_setcurrentprofileaction-1.0.0.jar ../$FOLD/obssuite_setcurrentprofileaction.jar

  cd ../setcurrentscene && mvn clean package
  mv target/obssuite_setcurrentsceneaction-1.0.0.jar ../$FOLD/obssuite_setcurrentsceneaction.jar

  cd ../setcurrenttransition && mvn clean package
  mv target/obssuite_setcurrenttransitionaction-1.0.0.jar ../$FOLD/obssuite_setcurrenttransitionaction.jar

  cd ../setmute && mvn clean package
  mv target/obssuite_setmuteaction-1.0.0.jar ../$FOLD/obssuite_setmuteaction.jar

  cd ../setcurrentprofile && mvn clean package
  mv target/obssuite_setcurrentprofileaction-1.0.0.jar ../$FOLD/obssuite_setcurrentprofileaction.jar

  cd ../setpreviewscene && mvn clean package
  mv target/obssuite_setpreviewsceneaction-1.0.0.jar ../$FOLD/obssuite_setpreviewsceneaction.jar

  cd ../setrecording && mvn clean package
  mv target/obssuite_setrecordingaction-1.0.0.jar ../$FOLD/obssuite_setrecordingaction.jar

  cd ../setreplaybuffer && mvn clean package
  mv target/obssuite_setreplaybufferaction-1.0.0.jar ../$FOLD/obssuite_setreplaybufferaction.jar

  cd ../setstreaming && mvn clean package
  mv target/obssuite_setstreamingaction-1.0.0.jar ../$FOLD/obssuite_setstreamingaction.jar

  cd ../setstudiomode && mvn clean package
  mv target/obssuite_setstudiomodeaction-1.0.0.jar ../$FOLD/obssuite_setstudiomodeaction.jar

  cd ../setvolume && mvn clean package
  mv target/obssuite_setvolumeaction-1.0.0.jar ../$FOLD/obssuite_setvolumeaction.jar
  popd || exit
}

playaudioclip() {
  pushd . || exit
  cd playaudioclipaction && mvn clean package
  mv target/playaudioclipaction-1.0.0.jar $FOLD/playaudioclipaction.jar
  popd || exit
}

runcommand() {
  pushd . || exit
  cd runcommandaction && mvn clean package
  mv target/runcommandaction-1.0.0.jar $FOLD/runcommandaction.jar
  popd || exit
}

textblock() {
  pushd . || exit
  cd textblockaction && mvn clean package
  mv target/textblockaction-1.0.0.jar $FOLD/textblockaction.jar
  popd || exit
}

twitter() {
  pushd . || exit
  cd twitteraction && mvn clean package
  mv target/twitteraction-1.0.0.jar $FOLD/twitteraction.jar
  popd || exit
}

twitchchat() {
  pushd . || exit
  cd twitch/twitch-chat-connect && mvn clean install package
  mv target/twitch-chat-connect-1.0.0.jar ../$FOLD/twitch-chat-connect.jar

  cd ../send-channel-msg && mvn clean install package
  mv target/twitch-send-channel-msg-1.0.0.jar ../$FOLD/twitch-send-channel-msg.jar

  cd ../clear-chat && mvn clean install package
  mv target/twitch-clear-chat-1.0.0.jar ../$FOLD/twitch-clear-chat.jar

  cd ../set-color && mvn clean install package
  mv target/twitch-set-color-1.0.0.jar ../$FOLD/twitch-set-color.jar

  cd ../whisper && mvn clean install package
  mv target/twitch-whisper-1.0.0.jar ../$FOLD/twitch-whisper.jar

  cd ../unraid && mvn clean install package
  mv target/twitch-unraid-1.0.0.jar ../$FOLD/twitch-unraid.jar

  cd ../unhost && mvn clean install package
  mv target/twitch-unhost-1.0.0.jar ../$FOLD/twitch-unhost.jar

  cd ../add-stream-marker && mvn clean install package
  mv target/twitch-add-stream-marker-1.0.0.jar ../$FOLD/twitch-add-stream-marker.jar
  popd || exit
}

websiteaction() {
  pushd . || exit
  cd websiteaction && mvn clean package
  mv target/websiteaction-1.0.0.jar $FOLD/websiteaction.jar
  popd || exit
}

case "$1" in
hotkey)
  hotkey
  ;;
mediakey)
  mediakey
  ;;
playaudioclip)
  playaudioclip
  ;;
runcommand)
  runcommand
  ;;
textblock)
  textblock
  ;;
twitter)
  twitter
  ;;
website)
  website
  ;;
obssuite)
  obssuite
  ;;
twitchchat)
  twitchchat
  ;;
*)
  # build all actions as default
  hotkey
  mediakey
  obssuite
  playaudioclip
  runcommand
  textblock
  twitter
  twitchchat
  website
  ;;
esac
