package com.stream_pi.clockaction;


import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ClockAction extends NormalAction
{
    public ClockAction()
    {
        setName("Clock Action");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/essential-actions");
        setVersion(new Version(1,0,0));
        setServerButtonGraphic("fas-clock");
        setCategory("System");
    }

    private String[] str;
    
    @Override
    public void initProperties() throws MinorException
    {
        
    }
    
    @Override
    public void onActionClicked() throws MinorException
    {
        try{
            while(true)
            {
                str = Calendar.getInstance().getTime().toString().split(" ");
                this.setDisplayText(str[3]);
                this.saveClientAction();
                TimeUnit.SECONDS.sleep(1);
            }
            } catch (Exception ex){
                throw new MinorException(ex.getMessage());
        }
    }
}