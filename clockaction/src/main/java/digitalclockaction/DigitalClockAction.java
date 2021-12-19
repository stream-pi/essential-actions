package digitalclockaction;

import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.action_api.externalplugin.inputevent.StreamPiInputEvent;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DigitalClockAction extends NormalAction
{
    public DigitalClockAction()
    {
        setName("Digital Clock");
        setCategory("Demo");
        setAuthor("rnayabed");
        setVersion(new Version(1, 0, 0));
    }

    @Override
    public void initProperties() throws MinorException
    {
        ListProperty defaultStateProperty = new ListProperty("default_state");
        defaultStateProperty.setDisplayName("Default State");
        defaultStateProperty.setListValue(List.of(
                new ListValue("Time 24 HR"),
                new ListValue("Time 12 HR"),
                new ListValue("Date")
        ));
        defaultStateProperty.setDefaultSelectedIndex(0);

        BooleanProperty allowCyclingProperty = new BooleanProperty("allow_cycling");
        allowCyclingProperty.setDisplayName("Allow cycling with touch gestures");
        allowCyclingProperty.setDefaultValue(true);

        addClientProperties(defaultStateProperty, allowCyclingProperty);
    }

    @Override
    public void onClientConnected() throws MinorException
    {
        currentState.set(getClientProperties().getSingleProperty("default_state").getSelectedIndex());
        displayTime();
        scheduleToDisplayTime();
    }

    @Override
    public void onActionSavedFromServer() throws MinorException
    {
        currentState.set(getClientProperties().getSingleProperty("default_state").getSelectedIndex());
        displayTime();
    }

    @Override
    public void onActionCreate()
    {
        try
        {
            currentState.set(0);
            displayTime();
            scheduleToDisplayTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void scheduleToDisplayTime()
    {
        long currentSystemTime = System.currentTimeMillis();
        System.out.println("CURRENT SYSTEM TIME: "+currentSystemTime);
        long delay = 60000 - (currentSystemTime % 60000);
        System.out.println("SCHEDULED! @ "+delay);
        submitToExecutorService(()->{
            try
            {
                Thread.sleep(delay);
                System.out.println("ASDNJASNDKNAS : "+isConnectedToClient());
                if (!isConnectedToClient())
                {
                    return;
                }

                System.out.println("RUN!!");
                displayTime();
                scheduleToDisplayTime();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    private final AtomicInteger currentState = new AtomicInteger(0);

    private long lastSystemCurrentMillis = -1;

    private final SimpleDateFormat format12Hr = new SimpleDateFormat("hh:mm a");
    private final SimpleDateFormat format24Hr = new SimpleDateFormat("HH:mm");

    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy");

    private synchronized void displayTime() throws MinorException
    {
        if(currentState.get() == 0)
        {
            updateTemporaryDisplayText(format24Hr.format(new Date()));
        }
        else if(currentState.get() == 1)
        {
            updateTemporaryDisplayText(format12Hr.format(new Date()));
        }
        else if(currentState.get() == 2)
        {
            updateTemporaryDisplayText(formatDate.format(new Date()));
        }
    }

    @Override
    public void onInputEventReceived(StreamPiInputEvent streamPiInputEvent) throws MinorException
    {
        if(!getClientProperties().getSingleProperty("allow_cycling").getBoolValue() || isBreak(streamPiInputEvent.getEventType())) return;

        System.out.println("DONT BREAK! 2"+streamPiInputEvent.getEventType());

        if (List.of(MouseEvent.MOUSE_CLICKED, TouchEvent.TOUCH_RELEASED, SwipeEvent.SWIPE_LEFT, SwipeEvent.SWIPE_UP).contains(streamPiInputEvent.getEventType()))
        {
            changeState(true);
        }
        else if (List.of(SwipeEvent.SWIPE_RIGHT, SwipeEvent.SWIPE_DOWN).contains(streamPiInputEvent.getEventType()))
        {
            System.out.println("LEFT");
            changeState(false);
        }
        displayTime();
    }

    private void changeState(boolean isIncrease)
    {
        if (isIncrease)
        {
            if (currentState.get() == 2)
            {
                currentState.set(0);
            }
            else
            {
                currentState.set(currentState.get() + 1);
            }
        }
        else
        {
            if (currentState.get() == 0)
            {
                currentState.set(2);
            }
            else
            {
                currentState.set(currentState.get() -1);
            }
        }
    }

    private boolean isBreak(EventType<?> eventType)
    {
        System.out.println(eventType+"ASDLANSDKNASDKJn");
        if(!List.of(MouseEvent.MOUSE_CLICKED, TouchEvent.TOUCH_RELEASED, SwipeEvent.SWIPE_LEFT, SwipeEvent.SWIPE_RIGHT, SwipeEvent.SWIPE_UP, SwipeEvent.SWIPE_DOWN).contains(eventType))
        {
            return true;
        }

        long tmpLastSystemCurrentMillis = lastSystemCurrentMillis;
        lastSystemCurrentMillis = System.currentTimeMillis();

        if (tmpLastSystemCurrentMillis < 0)
        {
            return false;
        }
        else
        {
            long diff = System.currentTimeMillis() - tmpLastSystemCurrentMillis;

            System.out.println("DIFF :"+diff);

            if (diff > 500)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}
