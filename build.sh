#!/bin/bash

FOLD=PreBuiltPlugins
DEPS=Dependencies

hotkey() {
  pushd . || exit
  cd hotkeyaction && mvn clean -Dmaven.test.skip package
  mv target/hotkeyaction-*.jar ../$FOLD/hotkeyaction.jar
  popd || exit
}

media_key() {
  pushd . || exit
  cd mediakeyaction && mvn clean -Dmaven.test.skip package
  mv target/mediakeyaction-*.jar ../$FOLD/mediakeyaction.jar
  popd || exit
}

obs_suite() {
  pushd . || exit
  cd obssuite/mother && mvn clean install -Dmaven.test.skip package
  mv target/obssuite_motheraction-*.jar ../../$FOLD/obssuite_motheraction.jar

  cd ../setcurrentprofile && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setcurrentprofileaction-*.jar ../../$FOLD/obssuite_setcurrentprofileaction.jar

  cd ../setcurrentscene && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setcurrentsceneaction-*.jar ../../$FOLD/obssuite_setcurrentsceneaction.jar

  cd ../setcurrenttransition && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setcurrenttransitionaction-*.jar ../../$FOLD/obssuite_setcurrenttransitionaction.jar

  cd ../setmute && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setmuteaction-*.jar ../../$FOLD/obssuite_setmuteaction.jar

  cd ../setcurrentprofile && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setcurrentprofileaction-*.jar ../../$FOLD/obssuite_setcurrentprofileaction.jar

  cd ../setpreviewscene && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setpreviewsceneaction-*.jar ../../$FOLD/obssuite_setpreviewsceneaction.jar

  cd ../setrecording && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setrecordingaction-*.jar ../../$FOLD/obssuite_setrecordingaction.jar

  cd ../setreplaybuffer && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setreplaybufferaction-*.jar ../../$FOLD/obssuite_setreplaybufferaction.jar

  cd ../setstreaming && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setstreamingaction-*.jar ../../$FOLD/obssuite_setstreamingaction.jar

  cd ../setstudiomode && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setstudiomodeaction-*.jar ../../$FOLD/obssuite_setstudiomodeaction.jar

  cd ../setvolume && mvn clean -Dmaven.test.skip package
  mv target/obssuite_setvolumeaction-*.jar ../../$FOLD/obssuite_setvolumeaction.jar
  popd || exit
}

play_audio_clip() {
  pushd . || exit
  cd playaudioclipaction && mvn clean -Dmaven.test.skip package
  mv target/playaudioclipaction-*.jar ../$FOLD/playaudioclipaction.jar
  popd || exit
}

run_command() {
  pushd . || exit
  cd runcommandaction && mvn clean -Dmaven.test.skip package
  mv target/runcommandaction-*.jar ../$FOLD/runcommandaction.jar
  popd || exit
}

text_block() {
  pushd . || exit
  cd textblockaction && mvn clean -Dmaven.test.skip package
  mv target/textblockaction-*.jar ../$FOLD/textblockaction.jar
  popd || exit
}

twitch_chat() {
  pushd . || exit
  cd twitch/twitch-chat-connect && mvn clean install -Dmaven.test.skip package
  mv target/twitch-chat-connect-*.jar ../../$FOLD/twitch-chat-connect.jar

  cd ../send-channel-msg && mvn clean -Dmaven.test.skip package
  mv target/twitch-send-channel-msg-*.jar ../../$FOLD/twitch-send-channel-msg.jar

  cd ../clear-chat && mvn clean -Dmaven.test.skip package
  mv target/twitch-clear-chat-*.jar ../../$FOLD/twitch-clear-chat.jar

  cd ../set-color && mvn clean -Dmaven.test.skip package
  mv target/twitch-set-color-*.jar ../../$FOLD/twitch-set-color.jar

  cd ../whisper && mvn clean -Dmaven.test.skip package
  mv target/twitch-whisper-*.jar ../../$FOLD/twitch-whisper.jar

  cd ../unraid && mvn clean -Dmaven.test.skip package
  mv target/twitch-unraid-*.jar ../../$FOLD/twitch-unraid.jar

  cd ../unhost && mvn clean -Dmaven.test.skip package
  mv target/twitch-unhost-*.jar ../../$FOLD/twitch-unhost.jar

  cd ../add-stream-marker && mvn clean -Dmaven.test.skip package
  mv target/twitch-add-stream-marker-*.jar ../../$FOLD/twitch-add-stream-marker.jar

  cd ../host-channel && mvn clean -Dmaven.test.skip package
  mv target/twitch-host-channel-*.jar ../../$FOLD/twitch-host-channel.jar

  cd ../raid-channel && mvn clean -Dmaven.test.skip package
  mv target/twitch-raid-channel-*.jar ../../$FOLD/twitch-raid-channel.jar

  cd ../start-commercial && mvn clean -Dmaven.test.skip package
  mv target/twitch-start-commercial-*.jar ../../$FOLD/twitch-start-commercial.jar

  cd ../subs-only && mvn clean -Dmaven.test.skip package
  mv target/twitch-subs-only-*.jar ../../$FOLD/twitch-subs-only.jar

  cd ../slow-mode && mvn clean -Dmaven.test.skip package
  mv target/twitch-slow-mode-*.jar ../../$FOLD/twitch-slow-mode.jar

  popd || exit
}

twitter() {
  pushd . || exit
  cd twitteraction && mvn clean -Dmaven.test.skip package
  mv target/twitteraction-*.jar ../$FOLD/twitteraction.jar
  popd || exit
}

website() {
  pushd . || exit
  cd websiteaction && mvn clean -Dmaven.test.skip package
  mv target/websiteaction-*.jar ../$FOLD/websiteaction.jar
  popd || exit
}

mkdir -p $FOLD
rm -rf "${FOLD:?}/"*
cp $DEPS/* $FOLD/

case "$1" in
hotkey)
  hotkey
  ;;
media_key)
  media_key
  ;;
obs_suite)
  obs_suite
  ;;
play_audio_clip)
  play_audio_clip
  ;;
run_command)
  run_command
  ;;
text_block)
  text_block
  ;;
twitch_chat)
  twitch_chat
  ;;
twitter)
  twitter
  ;;
website)
  website
  ;;
*)
  # build all actions as default
  hotkey
  media_key
  obs_suite
  play_audio_clip
  run_command
  text_block
  twitch_chat
  twitter
  website
  ;;
esac
