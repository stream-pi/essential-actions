package com.StreamPi.OBSSuite.Mother;

import com.StreamPi.ActionAPI.NormalAction.NormalAction;

public class Mother extends NormalAction {

    public Mother()
    {
        setName("Tweet");
        setCategory("Twitter");
        setAuthor("dubbadhar");
        setServerButtonGraphic("fab-twitter");
        setRepo("https://github.com/Stream-Pi/TwitterAction");
        setVersion(new Version(1,0,0));

    }

    @Override
    public void initProperties() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void initAction() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void onActionClicked() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
