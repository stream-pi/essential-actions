FOLD=$1

cd HotkeyAction && mvn clean package

mv target/HotkeyAction-1.0.0.jar $FOLD/HotkeyAction-1.0.0.jar

cd ../MediaKeyAction && mvn clean package

mv target/MediaKeyAction-1.0.0.jar $FOLD/MediaKeyAction-1.0.0.jar

cd ../PlayAudioClipAction && mvn clean package

mv target/PlayAudioClipAction-1.0.0.jar $FOLD/PlayAudioClipAction-1.0.0.jar

cd ../RunCommandAction && mvn clean package 

mv target/RunCommandAction-1.0.0.jar $FOLD/RunCommandAction-1.0.0.jar

cd ../TextBlockAction && mvn clean package

mv target/TextBlockAction-1.0.0.jar $FOLD/TextBlockAction-1.0.0.jar

cd ../TwitterAction && mvn clean package 

mv target/TwitterAction-1.0.0.jar $FOLD/TwitterAction-1.0.0.jar

cd ../WebsiteAction && mvn clean package

mv target/WebsiteAction-1.0.0.jar $FOLD/WebsiteAction-1.0.0.jar

cd ../OBSSuite/Mother && mvn clean install package 

mv target/OBSSuite-MotherAction-1.0.0.jar $FOLD/OBSSuite-MotherAction-1.0.0.jar

cd ../SetCurrentProfile && mvn clean package 

mv target/OBSSuite-SetCurrentProfileAction-1.0.0.jar $FOLD/OBSSuite-SetCurrentProfileAction-1.0.0.jar

cd ../SetCurrentScene && mvn clean package 

mv target/OBSSuite-SetCurrentSceneAction-1.0.0.jar $FOLD/OBSSuite-SetCurrentSceneAction-1.0.0.jar

cd ../SetCurrentTransition && mvn clean package 

mv target/OBSSuite-SetCurrentTransitionAction-1.0.0.jar $FOLD/OBSSuite-SetCurrentTransitionAction-1.0.0.jar

cd ../SetMute && mvn clean package 

mv target/OBSSuite-SetMuteAction-1.0.0.jar $FOLD/OBSSuite-SetMuteAction-1.0.0.jar

cd ../SetCurrentProfile && mvn clean package 

mv target/OBSSuite-SetCurrentProfileAction-1.0.0.jar $FOLD/OBSSuite-SetCurrentProfileAction-1.0.0.jar

cd ../SetPreviewScene && mvn clean package 

mv target/OBSSuite-SetPreviewSceneAction-1.0.0.jar $FOLD/OBSSuite-SetPreviewSceneAction-1.0.0.jar

cd ../SetRecording && mvn clean package

mv target/OBSSuite-SetRecordingAction-1.0.0.jar $FOLD/OBSSuite-SetRecordingAction-1.0.0.jar

cd ../SetReplayBuffer && mvn clean package 

mv target/OBSSuite-SetReplayBufferAction-1.0.0.jar $FOLD/OBSSuite-SetReplayBufferAction-1.0.0.jar

cd ../SetStreaming && mvn clean package 

mv target/OBSSuite-SetStreamingAction-1.0.0.jar $FOLD/OBSSuite-SetStreamingAction-1.0.0.jar

cd ../SetStudioMode && mvn clean package 

mv target/OBSSuite-SetStudioModeAction-1.0.0.jar $FOLD/OBSSuite-SetStudioModeAction-1.0.0.jar

cd ../SetVolume && mvn clean package

mv target/OBSSuite-SetVolumeAction-1.0.0.jar $FOLD/OBSSuite-SetVolumeAction-1.0.0.jar



