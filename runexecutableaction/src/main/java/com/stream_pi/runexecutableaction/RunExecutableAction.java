package com.stream_pi.runexecutableaction;

import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.platform.Platform;
import com.stream_pi.util.version.Version;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class RunExecutableAction extends NormalAction
{

    public RunExecutableAction()
    {
        setName("Run Executable");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-terminal");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void initProperties() throws MinorException
    {
        StringProperty executablePathProperty = new StringProperty("executable_location");
        executablePathProperty.setDisplayName("Executable Location");
        executablePathProperty.setControlType(ControlType.FILE_PATH);
        executablePathProperty.setExtensionFilters(
                new FileExtensionFilter("Executable", "*.*")
        );

        StringProperty argumentsProperty = new StringProperty("executable_arguments");
        argumentsProperty.setDisplayName("Arguments");

        addClientProperties(executablePathProperty, argumentsProperty);

        if(getServerConnection().getPlatform() == Platform.WINDOWS)
        {
            BooleanProperty runAsAdminProperty = new BooleanProperty("run_as_admin");
            runAsAdminProperty.setDisplayName("Run as Administrator");
            addClientProperties(runAsAdminProperty);
        }
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        String executableLocation = getClientProperties().getSingleProperty("executable_location").getStringValue();
        String arguments = getClientProperties().getSingleProperty("executable_arguments").getStringValue();

        if(executableLocation.isBlank())
        {
            throw new MinorException("Executable File location is empty");
        }


        File executableFile = new File(executableLocation);

        if(!executableFile.exists())
        {
            throw new MinorException("The File at given path '"+executableLocation+"' does not exist.");
        }

        try
        {

            if(getServerConnection().getPlatform() == Platform.WINDOWS)
            {
                String command = "powershell -Command \"cd '"+executableFile.getParentFile().toString()+
                        "'; Start-Process '"+executableLocation+"' -ArgumentList '"+arguments+"' ";


                getLogger().info("Full command : "+ command);

                boolean runAsAdmin = getClientProperties().getSingleProperty("run_as_admin").getBoolValue();
                if(runAsAdmin)
                {
                    command +="-Verb runAs\"";
                }
                else
                {
                    command +="\"";
                }

                getLogger().info("Running command : "+command);

                runCommand(command);
            }
            else
            {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.directory(executableFile.getParentFile());
                String[] args = arguments.split(" ");
                String[] finalArray = new String[args.length + 1];
                finalArray[0] = executableLocation;
                System.arraycopy(args, 0, finalArray, 1, args.length);
                getLogger().info("Full array : "+ Arrays.toString(finalArray));
                processBuilder.command(finalArray);
                processBuilder.start();
            }

        }
        catch (IOException e)
        {
            throw new MinorException(e.getMessage());
        }
    }

    private void runCommand(String command) throws IOException
    {
        Runtime rt = Runtime.getRuntime();
        rt.exec(command);
    }
}
