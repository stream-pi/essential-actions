package com.stream_pi.sleepmodeaction;

import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;

import java.io.IOException;
import java.util.prefs.Preferences;
import java.io.OutputStream;

public class SleepModeAction extends NormalAction
{
    public SleepModeAction()
    {
        setName("Sleep Mode");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/essential-actions");
        setVersion(new Version(1,0,0));
        setServerButtonGraphic("fas-folder-open");
        setCategory("System");
    }
    
    @Override
    public void onActionClicked() throws MinorException
    {
        try{
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("os")){
            Process p = Runtime.getRuntime().exec
                ("/bin/bash");
            String command = "osascript -e 'tell application \"System Events\"' " 
                + " -e \"sleep\" -e 'end tell'";
        
            OutputStream stdin = p.getOutputStream();
            stdin.write( command.getBytes() );
            stdin.flush();
            stdin.close();
            }
            else if(os.contains("windows"))
            {
                try{
                    Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
                } catch (IOException e){    
                    e.printStackTrace();
                }
            }
            else if(os.contains("unix"))
            {
                Runtime.getRuntime().exec("systemctl hibernate");
            }
            else
            {
                throw new MinorException("This action does not support " + os);
            }
        
        }catch( Exception e ) { 
            e.printStackTrace();
        }
    }
}
