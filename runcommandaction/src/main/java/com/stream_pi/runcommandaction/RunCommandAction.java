package com.stream_pi.runcommandaction;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import java.io.IOException;

public class RunCommandAction extends NormalAction
{

    public RunCommandAction()
    {
        setName("Run Command");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-terminal");
        setHelpLink("https://github.com/stream-pi/essentialactions/blob/master/runcommandaction/README.md");
        setVersion(new Version(1,1,1));
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property commandProperty = new Property("command", Type.STRING);
        commandProperty.setDisplayName("Command");

        addClientProperties(commandProperty);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        String command = getClientProperties().getSingleProperty("command").getStringValue();

        try
        {
            runCommand(command);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new MinorException("Unable to run command \n\n"+command+"\n\n.Reason:\n"+e.getMessage());
        }
    }

    private void runCommand(String command) throws IOException
    {
        Runtime rt = Runtime.getRuntime();
        rt.exec(command);
    }
}
