package com.StreamPi.RunCommandAction;

import java.io.IOException;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Exception.MinorException;
import com.StreamPi.Util.Version.Version;


public class RunCommandAction extends NormalAction {

    public RunCommandAction()
    {
        setName("Run Command");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-volume-up");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
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
