package subs_only;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.roomstate.Roomstate;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.ToggleAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class SubsOnlyAction extends ToggleAction
{

    private final String channelNameKey = "channel_name_so";

    private Twirk twirk;

    public SubsOnlyAction()
    {
        setName("Toggle Subs-Only Chat");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink(TwitchChatCredentials.HELP_LINK);
    }


    @Override
    public void initProperties() throws MinorException
    {
        Property channelName = new Property(channelNameKey, Type.STRING);
        channelName.setDisplayName("Channel Name");
        channelName.setDefaultValueStr("channel_name");
        channelName.setCanBeBlank(false);

        addClientProperties(channelName);
    }

    @Override
    public void onToggleOn() throws MinorException
    {
        try
        {
            connectToChannel();
            twirk.channelMessage("/subscribers");
        } catch (Exception ex)
        {
            setCurrentStatus(false);
            throw new MinorException("Failed to enable subscribers only mode.", "Please try again.");
        }
    }

    @Override
    public void onToggleOff() throws MinorException
    {
        try
        {
            connectToChannel();
            twirk.channelMessage("/subscribersoff");
        } catch (Exception ex)
        {
            setCurrentStatus(true);
            throw new MinorException("Failed to disable subscribers only mode.", "Please try again.");
        }
    }

    private void connectToChannel() throws Exception
    {
        if (twirk != null)
        {
            if (!twirk.isConnected())
            {
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
                        setCurrentStatus(roomstate.getSubMode() == 1);
                    } catch (MinorException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            });
            twirk.connect();
        } catch (Exception ex)
        {
            throw new MinorException("Failed to connect to Twitch",
                    String.format("Could not connect to '%s' channel.", channel));
        }
    }

    @Override
    public void onShutDown() throws MinorException
    {
        if (twirk != null)
        {
            try
            {
                twirk.disconnect();
            } catch (Exception ex)
            {
                throw new MinorException("Twitch connection error", "Please try again.");
            }
        }
    }
}
