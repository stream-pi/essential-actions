@echo off

SET FOLD=PreBuiltPlugins
SET DEPS=Dependencies
mkdir -p %FOLD%
DEL /S "%FOLD%/"*
COPY %DEPS%/* %FOLD%/

IF "%1%"=="Hotkey" (CALL :Hotkey)
IF "%1%"=="MediaKey" (CALL :MediaKey)
IF "%1%"=="ObsSuite" (CALL :ObsSuite)
IF "%1%"=="PlayAudioClip" (CALL :PlayAudioClip)
IF "%1%"=="RunCommand" (CALL :RunCommand)
IF "%1%"=="TextBlock" (CALL :TextBlock)
IF "%1%"=="TwitchChat" (CALL :TwitchChat)
IF "%1%"=="Twitter" (CALL :Twitter)
IF "%1%"=="Website" (CALL :Website)
IF "%1%"=="" (
    CALL :Hotkey
    CALL :MediaKey
    CALL :ObsSuite
    CALL :PlayAudioClip
    CALL :RunCommand
    CALL :TextBlock
    CALL :TwitchChat
    CALL :Twitter
    CALL :Website
)

EXIT /B %ERRORLEVEL%

:Hotkey
pushd %CD%
cd hotkeyaction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\hotkeyaction-*.jar ..\%FOLD%\hotkeyaction.jar
popd
EXIT /B 0

:MediaKey
pushd %CD%
cd mediakeyaction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\mediakeyaction-*.jar ..\%FOLD%\mediakeyaction.jar
popd
EXIT /B 0

:ObsSuite
pushd %CD%
cd obssuite\mother
CALL ..\..\mvnw.cmd clean install -Dmaven.test.skip package
mv target\obssuite_motheraction-*.jar ..\..\%FOLD%\obssuite_motheraction.jar

cd ..\setcurrentprofile
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setcurrentprofileaction-*.jar ..\..\%FOLD%\obssuite_setcurrentprofileaction.jar

cd ..\setcurrentscene
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setcurrentsceneaction-*.jar ..\..\%FOLD%\obssuite_setcurrentsceneaction.jar

cd ..\setcurrenttransition
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setcurrenttransitionaction-*.jar ..\..\%FOLD%\obssuite_setcurrenttransitionaction.jar

cd ..\setmute
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setmuteaction-*.jar ..\..\%FOLD%\obssuite_setmuteaction.jar

cd ..\setcurrentprofile
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setcurrentprofileaction-*.jar ..\..\%FOLD%\obssuite_setcurrentprofileaction.jar

cd ..\setpreviewscene
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setpreviewsceneaction-*.jar ..\..\%FOLD%\obssuite_setpreviewsceneaction.jar

cd ..\setrecording
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setrecordingaction-*.jar ..\..\%FOLD%\obssuite_setrecordingaction.jar

cd ..\setreplaybuffer
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setreplaybufferaction-*.jar ..\..\%FOLD%\obssuite_setreplaybufferaction.jar

cd ..\setstreaming
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setstreamingaction-*.jar ..\..\%FOLD%\obssuite_setstreamingaction.jar

cd ..\setstudiomode
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setstudiomodeaction-*.jar ..\..\%FOLD%\obssuite_setstudiomodeaction.jar

cd ..\setvolume
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\obssuite_setvolumeaction-*.jar ..\..\%FOLD%\obssuite_setvolumeaction.jar
popd
EXIT /B 0

:PlayAudioClip
pushd %CD%
cd playaudioclipaction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\playaudioclipaction-*.jar ..\..\%FOLD%\playaudioclipaction.jar
popd
EXIT /B 0

:RunCommand
pushd %CD%
cd runcommandaction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\runcommandaction-*.jar ..\..\%FOLD%\runcommandaction.jar
popd
EXIT /B 0

:TextBlock
pushd %CD%
cd textblockaction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\textblockaction-*.jar ..\..\%FOLD%\textblockaction.jar
popd
EXIT /B 0

:TwitchChat
pushd %CD%
cd twitch\twitch-chat-connect
CALL ..\..\mvnw.cmd clean install -Dmaven.test.skip package
mv target\twitch-chat-connect-*.jar ..\..\%FOLD%\twitch-chat-connect.jar

cd %CD%.\send-channel-msg
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-send-channel-msg-*.jar ..\..\%FOLD%\twitch-send-channel-msg.jar

cd %CD%.\clear-chat
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-clear-chat-*.jar ..\..\%FOLD%\twitch-clear-chat.jar

cd %CD%.\set-color
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-set-color-*.jar ..\..\%FOLD%\twitch-set-color.jar

cd %CD%.\whisper
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-whisper-*.jar ..\..\%FOLD%\twitch-whisper.jar

cd %CD%.\unraid
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-unraid-*.jar ..\..\%FOLD%\twitch-unraid.jar

cd %CD%.\unhost
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-unhost-*.jar ..\..\%FOLD%\twitch-unhost.jar

cd %CD%.\add-stream-marker
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-add-stream-marker-*.jar ..\..\%FOLD%\twitch-add-stream-marker.jar

cd %CD%.\host-channel
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-host-channel-*.jar ..\..\%FOLD%\twitch-host-channel.jar

cd %CD%.\raid-channel
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-raid-channel-*.jar ..\..\%FOLD%\twitch-raid-channel.jar

cd %CD%.\start-commercial
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-start-commercial-*.jar ..\..\%FOLD%\twitch-start-commercial.jar

cd %CD%.\subs-only
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-subs-only-*.jar ..\..\%FOLD%\twitch-subs-only.jar

cd %CD%.\slow-mode
CALL ..\..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitch-slow-mode-*.jar ..\..\%FOLD%\twitch-slow-mode.jar
popd
EXIT /B 0

:Twitter
pushd %CD%
cd twitteraction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\twitteraction-*.jar ..\..\%FOLD%\twitteraction.jar
popd
EXIT /B 0

:Website
pushd %CD%
cd websiteaction
CALL ..\mvnw.cmd clean -Dmaven.test.skip package
mv target\websiteaction-*.jar ..\..\%FOLD%\websiteaction.jar
popd
EXIT /B 0