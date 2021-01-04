package CurrentProfile;

import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Version.Version;

public class CurrentProfile extends NormalAction {

    public CurrentProfile()
    {
        setName("Set Current Profile");
        setCategory("OBS");
        setVisibilityInServerSettingsPane(false);
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
