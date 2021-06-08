@echo off

SET FOLD=BuiltPlugins
SET DEPS=Dependencies
SET quit=true
rmdir /q /s %FOLD%
mkdir %FOLD%

copy %DEPS%\* %FOLD%\

if "%1%"=="hotkey" (goto hotkeyaction)
if "%1%"=="mediakey" (goto mediakeyaction)
if "%1%"=="obs" (goto obssuite)
if "%1%"=="playaudioclip" (goto playaudioclipaction)
if "%1%"=="runcommand" (goto runcommandaction)
if "%1%"=="textblock" (goto textblockaction)
if "%1%"=="twitch-talk" (goto twitch)
if "%1%"=="twitter" (goto twitteraction)
if "%1%"=="website" (goto websiteaction)
if "%1%"=="openfile" (goto openfileaction)

if NOT "%1%"=="" (
    echo Invalid argument %1%
    EXIT /B 0
)

set quit=false

goto hotkeyaction
goto mediakeyaction
goto obssuite
goto playaudioclipaction
goto runcommandaction
goto textblockaction
goto twitch
goto twitteraction
goto websiteaction
goto openfileaction

:hotkeyaction
pushd %CD%
cd hotkeyaction
call mvn clean -Dmaven.test.skip package
move target\hotkeyaction-*.jar ..\%FOLD%\hotkeyaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:mediakeyaction
pushd %CD%
cd mediakeyaction
call mvn clean -Dmaven.test.skip package
move target\mediakeyaction-*.jar ..\%FOLD%\mediakeyaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:obssuite
pushd %CD%
cd obssuite\mother
CALL mvn clean install -Dmaven.test.skip package
move target\obssuite_motheraction-*.jar ..\..\%FOLD%\obssuite_motheraction.jar

cd ..\setcurrentprofile
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setcurrentprofileaction-*.jar ..\..\%FOLD%\obssuite_setcurrentprofileaction.jar

cd ..\setcurrentscene
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setcurrentsceneaction-*.jar ..\..\%FOLD%\obssuite_setcurrentsceneaction.jar

cd ..\setcurrenttransition
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setcurrenttransitionaction-*.jar ..\..\%FOLD%\obssuite_setcurrenttransitionaction.jar

cd ..\togglemute
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_togglemuteaction-*.jar ..\..\%FOLD%\obssuite_togglemuteaction.jar

cd ..\setcurrentprofile
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setcurrentprofileaction-*.jar ..\..\%FOLD%\obssuite_setcurrentprofileaction.jar

cd ..\setpreviewscene
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setpreviewsceneaction-*.jar ..\..\%FOLD%\obssuite_setpreviewsceneaction.jar

cd ..\togglerecording
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_togglerecordingaction-*.jar ..\..\%FOLD%\obssuite_togglerecordingaction.jar

cd ..\setreplaybuffer
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setreplaybufferaction-*.jar ..\..\%FOLD%\obssuite_setreplaybufferaction.jar

cd ..\togglestreaming
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_togglestreamingaction-*.jar ..\..\%FOLD%\obssuite_togglestreamingaction.jar

cd ..\togglestudiomode
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_togglestudiomodeaction-*.jar ..\..\%FOLD%\obssuite_togglestudiomodeaction.jar

cd ..\setvolume
CALL mvn clean -Dmaven.test.skip package
move target\obssuite_setvolumeaction-*.jar ..\..\%FOLD%\obssuite_setvolumeaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:playaudioclipaction
pushd %CD%
cd playaudioclipaction
call mvn clean -Dmaven.test.skip package
move target\playaudioclipaction-*.jar ..\%FOLD%\playaudioclipaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:runcommandaction
pushd %CD%
cd runcommandaction
call mvn clean -Dmaven.test.skip package
move target\runcommandaction-*.jar ..\%FOLD%\runcommandaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:textblockaction
pushd %CD%
cd textblockaction
call mvn clean -Dmaven.test.skip package
move target\textblockaction-*.jar ..\%FOLD%\textblockaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:twitch
pushd %CD%
cd twitch\twitch-chat-connect
CALL mvn clean install -Dmaven.test.skip package
move target\twitch-chat-connect-*.jar ..\..\%FOLD%\twitch-chat-connect.jar

cd ..\send-channel-msg
CALL mvn clean -Dmaven.test.skip package
move target\twitch-send-channel-msg-*.jar ..\..\%FOLD%\twitch-send-channel-msg.jar

cd ..\clear-chat
CALL mvn clean -Dmaven.test.skip package
move target\twitch-clear-chat-*.jar ..\..\%FOLD%\twitch-clear-chat.jar

cd ..\set-color
CALL mvn clean -Dmaven.test.skip package
move target\twitch-set-color-*.jar ..\..\%FOLD%\twitch-set-color.jar

cd ..\whisper
CALL mvn clean -Dmaven.test.skip package
move target\twitch-whisper-*.jar ..\..\%FOLD%\twitch-whisper.jar

cd ..\unraid
CALL mvn clean -Dmaven.test.skip package
move target\twitch-unraid-*.jar ..\..\%FOLD%\twitch-unraid.jar

cd ..\unhost
CALL mvn clean -Dmaven.test.skip package
move target\twitch-unhost-*.jar ..\..\%FOLD%\twitch-unhost.jar

cd ..\add-stream-marker
CALL mvn clean -Dmaven.test.skip package
move target\twitch-add-stream-marker-*.jar ..\..\%FOLD%\twitch-add-stream-marker.jar

cd ..\host-channel
CALL mvn clean -Dmaven.test.skip package
move target\twitch-host-channel-*.jar ..\..\%FOLD%\twitch-host-channel.jar

cd ..\raid-channel
CALL mvn clean -Dmaven.test.skip package
move target\twitch-raid-channel-*.jar ..\..\%FOLD%\twitch-raid-channel.jar

cd ..\start-commercial
CALL mvn clean -Dmaven.test.skip package
move target\twitch-start-commercial-*.jar ..\..\%FOLD%\twitch-start-commercial.jar

cd ..\subs-only
CALL mvn clean -Dmaven.test.skip package
move target\twitch-subs-only-*.jar ..\..\%FOLD%\twitch-subs-only.jar

cd ..\slow-mode
CALL mvn clean -Dmaven.test.skip package
move target\twitch-slow-mode-*.jar ..\..\%FOLD%\twitch-slow-mode.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:twitteraction
pushd %CD%
cd twitteraction
call mvn clean -Dmaven.test.skip package
move target\twitteraction-*.jar ..\%FOLD%\twitteraction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:websiteaction
pushd %CD%
cd websiteaction
call mvn clean -Dmaven.test.skip package
move target\websiteaction-*.jar ..\%FOLD%\websiteaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)

:openfileaction
pushd %CD%
cd openfileaction
call mvn clean -Dmaven.test.skip package
move target\openfileaction-*.jar ..\%FOLD%\openfileaction.jar
popd
if "%quit%" == "true" (EXIT /B 0)