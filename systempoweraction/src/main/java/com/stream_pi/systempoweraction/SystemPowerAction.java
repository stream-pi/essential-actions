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

        addClientProperties(status);
    }
    
    @Override
    public void onActionClicked() throws MinorException
    {
        Platform plat = getPlatform();
        String state = states.get(getClientProperties().getSingleProperty("state").getSelectedIndex()).getName().toString();
        switch (state)
        {
            case "Shutdown":
                Shutdown(plat);
                break;
            
            case "Restart":
                Restart(plat);
                break;
                
            case "Sleep":
                Sleep(plat);
                break;     
        }
    }
    
    public void Shutdown(Platform plat) throws MinorException
    {
        String shutdownCommand = new String();
        try{
            if (null != plat) switch (plat) {
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
                break;
            case LINUX:
                shutdownCommand = "shutdown -h now";
            case UNKNOWN:
                throw new RuntimeException("Unsupported operating system.");
            default:
                break;
                }
            Runtime.getRuntime().exec(shutdownCommand);
            } catch (IOException e){
                    throw new MinorException(e.getMessage());
            }
    }
    
    public void Sleep(Platform plat) throws MinorException
    {
        try{
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
                    } catch (IOException e){
                        throw new MinorException(e.getMessage());
                    }   break;
                case LINUX:
                    Runtime.getRuntime().exec("systemctl suspend");
                    break;
                default:
                    throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
        
        }catch( Exception e ) { 
            throw new MinorException(e.getMessage());
        }
    }
    
    public void Restart(Platform plat) throws MinorException
    {
        try{
            if(null == plat){
                throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
            else switch (plat) {
                case LINUX:
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
                default:
                    throw new MinorException("This action does not support " + System.getProperty("os.name"));
            }
        
        }catch( Exception e ) { 
            throw new MinorException(e.getMessage());
        }
    }
}
