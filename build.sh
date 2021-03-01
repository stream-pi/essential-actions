FOLD=../PreBuiltPlugins

pushd . || exit
cd hotkeyaction && mvn clean package
mv target/hotkeyaction-1.0.0.jar $FOLD/hotkeyaction.jar

cd ../mediakeyaction && mvn clean package
mv target/mediakeyaction-1.0.0.jar $FOLD/mediakeyaction.jar

cd ../playaudioclipaction && mvn clean package
mv target/playaudioclipaction-1.0.0.jar $FOLD/playaudioclipaction.jar

cd ../runcommandaction && mvn clean package
mv target/runcommandaction-1.0.0.jar $FOLD/runcommandaction.jar

cd ../textblockaction && mvn clean package
mv target/textblockaction-1.0.0.jar $FOLD/textblockaction.jar

cd ../twitteraction && mvn clean package
mv target/twitteraction-1.0.0.jar $FOLD/twitteraction.jar

cd ../websiteaction && mvn clean package
mv target/websiteaction-1.0.0.jar $FOLD/websiteaction.jar
popd || exit

## OBS Suite
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

# Twitch Chat
pushd . || exit
cd twitch/twitch-chat-connect && mvn clean install package
mv target/twitch-chat-connect-1.0.0.jar ../$FOLD/twitch-chat-connect.jar

cd ../send-channel-msg && mvn clean package
mv target/twitch-send-channel-msg-1.0.0.jar ../$FOLD/twitch-send-channel-msg.jar
popd || exit
