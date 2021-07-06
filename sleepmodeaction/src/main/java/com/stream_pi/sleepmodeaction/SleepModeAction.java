package com.stream_pi.sleepmodeaction;

import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.platform.Platform;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import java.io.OutputStream;

public class SleepModeAction extends NormalAction
{
    public SleepModeAction()
    {
        setName("Sleep Mode");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/essential-actions");
        setVersion(new Version(1,0,0));
        setCategory("System");
    }
    
    @Override
    public void onActionClicked() throws MinorException
    {
        try{
            Platform plat;
            plat = getPlatform();
            if(null == plat){
                throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
            else switch (plat) {
                case MAC:
                    Process p = Runtime.getRuntime().exec
                        ("/bin/bash");
                    String command = "osascript -e 'tell application \"System Events\"' "
                            + " -e \"sleep\" -e 'end tell'";
                    OutputStream stdin = p.getOutputStream();
                    stdin.write( command.getBytes() );
                    stdin.flush();
                    stdin.close();
                    break;
                case WINDOWS:
                    try{
                        Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
                    } catch (Exception e){
                        e.printStackTrace();
                    }   break;
                case LINUX:
                    Runtime.getRuntime().exec("systemctl hibernate");
                    break;
                default:
                    throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
        
        }catch( Exception e ) { 
            e.printStackTrace();
        }
    }
}
