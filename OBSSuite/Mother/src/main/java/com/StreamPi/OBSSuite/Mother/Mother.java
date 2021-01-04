package com.StreamPi.OBSSuite.Mother;

import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.OBSSuite.Mother.API;
import com.StreamPi.Util.Version.Version;

public class Mother extends NormalAction
{

    public Mother()
    {
        setName("OBS Plugin");
        setCategory("OBS");
        setVisibilityInPluginsPane(false);
        setVisibilityInServerSettingsPane(true);
        setAuthor("rnayabed");
        setRepo("https://github.com/Stream-Pi/EssentialActions");
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
