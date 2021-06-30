package addstreammarker;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;
import connect.chat.TwitchChatCredentials;

public class AddStreamMarkerAction extends NormalAction
{

    private final String channelNameKey = "channel_name_asm";
    private final String descriptionKey = "description_asm";

    private Twirk twirk;

    public AddStreamMarkerAction()
    {
        setName("Add stream marker");
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

        Property description = new Property(descriptionKey, Type.STRING);
        description.setDisplayName("Description (Optional, max 140 chars)");

        addClientProperties(channelName, description);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        final TwitchChatCredentials.ChatCredentials credentials = TwitchChatCredentials.getCredentials();
        credentials.ensureCredentialsInitialized();

        final String channel = getClientProperties().getSingleProperty(channelNameKey).getStringValue();
        final String description = getClientProperties().getSingleProperty(descriptionKey).getStringValue();

        try
        {
            twirk = new TwirkBuilder(channel, credentials.getNickname(), credentials.getOauthToken()).build();
            twirk.connect();

            if (description != null && !description.isEmpty()) {
                twirk.channelMessage(String.format("/marker %s", description));
            } else {
                twirk.channelMessage("/marker");
            }
        } catch (Exception ex)
        {
            throw new MinorException(
                    "Failed to add marker to stream\nCould not add stream marker, please try again."
            );
        }
    }

    @Override
    public void onShutDown() throws MinorException
    {
        if (twirk != null) {
            try
            {
                twirk.disconnect();
            }
            catch (Exception ex)
            {
                throw new MinorException("Twitch connection error - Please try again.");
            }
        }
    }
}
