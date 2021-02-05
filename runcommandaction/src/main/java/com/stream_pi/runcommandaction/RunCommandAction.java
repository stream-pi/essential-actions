package com.stream_pi.runcommandaction;

import com.stream_pi.actionapi.actionproperty.property.Property;
import com.stream_pi.actionapi.actionproperty.property.Type;
import com.stream_pi.actionapi.normalaction.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.version.Version;


public class RunCommandAction extends NormalAction {

    public RunCommandAction()
    {
        setName("Run Command");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-terminal");
        setHelpLink("https://github.com/Stream-Pi/EssentialActions");
        setVersion(new Version(1,0,0));
    }

    @Override
    public void initProperties() throws Exception {
        Property commandProperty = new Property("command", Type.STRING);
        commandProperty.setDisplayName("Command");

        addClientProperties(commandProperty);
    }

    @Override
    public void initAction() throws Exception {


    }

    @Override
    public void onActionClicked() throws Exception 
    {
        int rv = runCommand(getClientProperties().getSingleProperty("command").getStringValue());

        System.out.println(rv);
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }

    private int runCommand(String command) throws Exception
    {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command);
        return pr.waitFor();
    }
}
