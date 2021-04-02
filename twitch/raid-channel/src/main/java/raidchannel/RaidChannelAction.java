package raidchannel;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.StreamPiException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class RaidChannelAction extends NormalAction
{

    private final String channelKey = "channel_rc";
    private final String channelToRaidKey = "channel_to_raid_rc";

    private Twirk twirk;

    public RaidChannelAction()
    {
        setName("Raid Channel");
        setCategory("Twitch Chat");
        setVisibilityInServerSettingsPane(false);
        setAuthor("j4ckofalltrades");
        setVersion(new Version(1, 0, 0));
        setHelpLink("https://github.com/stream-pi/essentialactions#twitch-chat-integration");
    }

    @Override
    public void initProperties() throws Exception
    {
        Property channel = new Property(channelKey, Type.STRING);
        channel.setDisplayName("Channel");
        channel.setDefaultValueStr("channel");
        channel.setCanBeBlank(false);

        Property channelToRaid = new Property(channelToRaidKey, Type.STRING);
        channelToRaid.setDisplayName("Channel to raid");
        channelToRaid.setDefaultValueStr("channel_to_raid");
        channelToRaid.setCanBeBlank(false);

        addClientProperties(channel, channelToRaid);
    }

    @Override
    public void initAction() throws Exception
    {

    }

    @Override
    public void onActionClicked() throws Exception
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelKey).getStringValue();
        final String channelToRaid = getClientProperties().getSingleProperty(channelToRaidKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();
            twirk.channelMessage(String.format("/raid %s", channelToRaid));
        } catch (Exception ex)
        {
            throw new StreamPiException(
                    "Failed to raid channel",
                    String.format("Could not raid channel '%s', please try again.", channelToRaid));
        }
    }

    @Override
    public void onShutDown() throws Exception
    {
        if (twirk != null) {
            try
            {
                twirk.disconnect();
            } catch (Exception ex) {
                throw new StreamPiException("Twitch connection error", "Please try again.");
            }
        }
    }
}
