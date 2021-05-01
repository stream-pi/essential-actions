package slow_mode;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.roomstate.Roomstate;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class SlowModeAction extends ToggleAction
{

    private final String channelNameKey = "channel_name_sm";
    private final String slowModeDurationKey = "slow_mode_duration";

    private Twirk twirk;

    public SlowModeAction()
    {
        setName("Toggle Slow Mode");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property channelName = new Property(channelNameKey, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        Property durationInSecs = new Property(slowModeDurationKey, Type.INTEGER);
        durationInSecs.setDefaultValueInt(600);
        durationInSecs.setDisplayName("Duration (in seconds)");

        addClientProperties(channelName, durationInSecs);
    }

    @Override
    public void onToggleOn() throws Exception
    {
        try
        {
            connectToChannel();
            final int duration = getClientProperties().getSingleProperty(slowModeDurationKey).getIntValue();
            twirk.channelMessage(String.format("/slow %d", duration));
        } catch (Exception ex)
        {
            setCurrentStatus(false);
            throw new StreamPiException("Failed to enable slow mode.", "Please try again.");
        }
    }

    @Override
    public void onToggleOff() throws Exception
    {
        try
        {
            connectToChannel();
            twirk.channelMessage("/slowoff");
        } catch (Exception ex)
        {
            setCurrentStatus(true);
            throw new StreamPiException("Failed to disable slow mode.", "Please try again.");
        }
    }

    private void connectToChannel() throws Exception
    {
        if (twirk != null) {
            if (!twirk.isConnected()) {
                twirk.connect();
            }

            return;
        }

        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();
        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.addIrcListener(new TwirkListener()
            {
                @Override
                public void onRoomstate(Roomstate roomstate)
                {
                    try
                    {
                        setCurrentStatus(roomstate.getSlowModeTimer() > 0);
                    } catch (MinorException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            });
            twirk.connect();
        } catch (Exception ex)
        {
            throw new StreamPiException("Failed to connect to Twitch",
                    String.format("Could not connect to '%s' channel.", channel));
        }
    }

    @Override
    public void onShutDown() throws Exception
    {
        if (twirk != null)
        {
            try
            {
                twirk.disconnect();
            } catch (Exception ex)
            {
                throw new StreamPiException("Twitch connection error", "Please try again.");
            }
        }
    }
}
