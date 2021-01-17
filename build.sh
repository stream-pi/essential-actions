FOLD=../PreBuiltPlugins

cd HotkeyAction && mvn clean package

mv target/HotkeyAction-1.0.0.jar $FOLD/HotkeyAction.jar

cd ../MediaKeyAction && mvn clean package

mv target/MediaKeyAction-1.0.0.jar $FOLD/MediaKeyAction.jar

cd ../PlayAudioClipAction && mvn clean package

mv target/PlayAudioClipAction-1.0.0.jar $FOLD/PlayAudioClipAction.jar

cd ../RunCommandAction && mvn clean package 

mv target/RunCommandAction-1.0.0.jar $FOLD/RunCommandAction.jar

cd ../TextBlockAction && mvn clean package

mv target/TextBlockAction-1.0.0.jar $FOLD/TextBlockAction.jar

cd ../TwitterAction && mvn clean package 

mv target/TwitterAction-1.0.0.jar $FOLD/TwitterAction.jar

cd ../WebsiteAction && mvn clean package

mv target/WebsiteAction-1.0.0.jar $FOLD/WebsiteAction.jar


# OBS Suite


cd ../OBSSuite/Mother && mvn clean install package 

mv target/OBSSuite-MotherAction-1.0.0.jar ../$FOLD/OBSSuite-MotherAction.jar

cd ../SetCurrentProfile && mvn clean package 

mv target/OBSSuite-SetCurrentProfileAction-1.0.0.jar ../$FOLD/OBSSuite-SetCurrentProfileAction.jar

cd ../SetCurrentScene && mvn clean package 

mv target/OBSSuite-SetCurrentSceneAction-1.0.0.jar ../$FOLD/OBSSuite-SetCurrentSceneAction.jar

cd ../SetCurrentTransition && mvn clean package 

mv target/OBSSuite-SetCurrentTransitionAction-1.0.0.jar ../$FOLD/OBSSuite-SetCurrentTransitionAction.jar

cd ../SetMute && mvn clean package 

mv target/OBSSuite-SetMuteAction-1.0.0.jar ../$FOLD/OBSSuite-SetMuteAction.jar

cd ../SetCurrentProfile && mvn clean package 

mv target/OBSSuite-SetCurrentProfileAction-1.0.0.jar ../$FOLD/OBSSuite-SetCurrentProfileAction.jar

cd ../SetPreviewScene && mvn clean package 

mv target/OBSSuite-SetPreviewSceneAction-1.0.0.jar ../$FOLD/OBSSuite-SetPreviewSceneAction.jar

cd ../SetRecording && mvn clean package

mv target/OBSSuite-SetRecordingAction-1.0.0.jar ../$FOLD/OBSSuite-SetRecordingAction.jar

cd ../SetReplayBuffer && mvn clean package 

mv target/OBSSuite-SetReplayBufferAction-1.0.0.jar ../$FOLD/OBSSuite-SetReplayBufferAction.jar

cd ../SetStreaming && mvn clean package 

mv target/OBSSuite-SetStreamingAction-1.0.0.jar ../$FOLD/OBSSuite-SetStreamingAction.jar

cd ../SetStudioMode && mvn clean package 

mv target/OBSSuite-SetStudioModeAction-1.0.0.jar ../$FOLD/OBSSuite-SetStudioModeAction.jar

cd ../SetVolume && mvn clean package

mv target/OBSSuite-SetVolumeAction-1.0.0.jar ../$FOLD/OBSSuite-SetVolumeAction.jar



