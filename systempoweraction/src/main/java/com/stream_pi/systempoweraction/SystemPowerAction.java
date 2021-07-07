package com.stream_pi.systempoweraction;

import java.util.ArrayList;
import java.util.Arrays;

import com.stream_pi.action_api.actionproperty.property.ListValue;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.platform.Platform;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import java.io.IOException;
import java.io.OutputStream;

public class SystemPowerAction extends NormalAction
{
    public SystemPowerAction()
    {
        setName("Power Action");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/essential-actions");
        setVersion(new Version(1,0,0));
        setCategory("System");
        
        states = new ArrayList<>();
        states.addAll(Arrays.asList(
                new ListValue("Shutdown"),
                new ListValue("Restart"),
                new ListValue("Sleep")
        ));
    }
    
    private final ArrayList<ListValue> states;
    
    @Override
    public void initProperties() throws MinorException
    {
        Property status = new Property("state", Type.LIST);
        status.setListValue(states);
        status.setDisplayName("Power State");
        
        Property customLinuxSleep = new Property("linuxsl", Type.STRING);
        customLinuxSleep.setDisplayName("Custom Linux Sleep Command");
        customLinuxSleep.setCanBeBlank(true);
        
        Property customLinuxRestart = new Property("linuxre", Type.STRING);
        customLinuxRestart.setDisplayName("Custom Linux Restart Command");
        customLinuxRestart.setCanBeBlank(true);

        Property customLinuxShutdown = new Property("linuxsh", Type.STRING);
        customLinuxShutdown.setDisplayName("Custom Linux Shutdown Command");
        customLinuxShutdown.setCanBeBlank(true);
        
        addClientProperties(status);
        addServerProperties(customLinuxSleep, customLinuxRestart, customLinuxShutdown);
    }
    
    @Override
    public void onActionClicked() throws MinorException
    {
        String state = states.get(getClientProperties().getSingleProperty("state").getSelectedIndex()).getName().toString();
        switch (state)
        {
            case "Shutdown":
                shutdown();
                break;
            
            case "Restart":
                restart();
                break;
                
            case "Sleep":
                sleep();
                break;     
        }
    }
    
    public void shutdown() throws MinorException
    {
        String shutdownCommand = "";
        try{
            switch (getPlatform()) {
            case MAC:
                    Process p = Runtime.getRuntime().exec
                         ("/bin/bash");
                     String command = "osascript -e 'tell application \"System Events\"' "
                             + " -e \"shut down\" -e 'end tell'";
                     OutputStream stdin = p.getOutputStream();
                     stdin.write( command.getBytes() );
                     stdin.flush();
                     stdin.close();
                break;
            case WINDOWS:
                shutdownCommand = "shutdown.exe -s -t 0";
                Runtime.getRuntime().exec(shutdownCommand);
                break;
            case LINUX:
                String shutdown = getClientProperties().getSingleProperty("linuxsh").getStringValue();
                if(shutdown != "")
                {
                    shutdownCommand = "shutdown -h now";
                    Runtime.getRuntime().exec(shutdownCommand); 
                }
                else
                {
                    Runtime.getRuntime().exec(shutdown);
                }
            case UNKNOWN:
                throw new RuntimeException("Unsupported operating system.");
            default:
                break;
                }
            } catch (IOException e){
                    throw new MinorException(e.getMessage());
            }
    }
    
    public void sleep() throws MinorException
    {
        try{
            switch (getPlatform()) {
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
                    } catch (IOException e){
                        throw new MinorException(e.getMessage());
                    }   break;
                case LINUX:
                    String sleep = getClientProperties().getSingleProperty("linuxsl").getStringValue();
                    if(sleep != "")
                    {
                        Runtime.getRuntime().exec("systemctl suspend");
                    }
                    else
                    {
                        Runtime.getRuntime().exec(sleep);
                    }
                    break;
                default:
                    throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
        
        }catch( Exception e ) { 
            throw new MinorException(e.getMessage());
        }
    }
    
    public void restart() throws MinorException
    {
        try{
            switch (getPlatform()) {
                case MAC:
                    Process p = Runtime.getRuntime().exec
                         ("/bin/bash");
                     String command = "osascript -e 'tell application \"System Events\"' "
                             + " -e \"restart\" -e 'end tell'";
                     OutputStream stdin = p.getOutputStream();
                     stdin.write( command.getBytes() );
                     stdin.flush();
                     stdin.close();
                    break;
                case WINDOWS:
                    Runtime.getRuntime().exec("shutdown -r");
                    break;
                case LINUX:
                    String restart = getClientProperties().getSingleProperty("linuxre").getStringValue();
                    if(restart != "")
                    {
                        Runtime.getRuntime().exec("shutdown -r now");
                    }
                    else
                    {
                        Runtime.getRuntime().exec(restart);
                    }
                    break;
                default:
                    throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
        
        }catch( Exception e ) { 
            throw new MinorException(e.getMessage());
        }
    }
}
