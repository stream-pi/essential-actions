package com.stream_pi.gitclone;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.FileExtensionFilter;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class GitClone extends NormalAction
{

    private Git call;


    public GitClone()
    {
        setName("Git - Git Clone");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/essentialactions");
        setServerButtonGraphic("fas-git");
        setVersion(new Version(1,0,1));

        setCategory("Git Integration");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property property1 = new Property("cloneURL", Type.STRING);
        property1.setDefaultValueStr("https://hmmmmyummy.com/somegitrepo");
        property1.setDisplayName("Git Repo URL");
        
        Property property2 = new Property("path", Type.STRING);
        property2.setDisplayName("Output Path");


        addClientProperties(
                property1,
                property2
        );
    }


    @Override
    public void onActionClicked() throws MinorException, InvalidRemoteException, TransportException, GitAPIException
    {
        CloneCommand git = Git.cloneRepository();
        Property property1 = getClientProperties().getSingleProperty("cloneURL");
        git.setURI(property1.getStringValue());
        git.setDirectory(new File(getClientProperties().getSingleProperty("path").getStringValue()));
        git.setCloneAllBranches(true);
        git.call();
    }
}
