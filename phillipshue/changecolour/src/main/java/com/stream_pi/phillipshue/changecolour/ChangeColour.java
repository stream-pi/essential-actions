package com.stream_pi.phillipshue.changecolour;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;

import io.github.zeroone3010.yahueaapi.*;
import io.github.zeroone3010.yahueapi.discovery.*;

import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

import java.scene.paint.Color;

public class ChangeColour extends NormalAction
{

    public ChangeColour()
    {
        setName("Phillips Hue - Change Color");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/");
        setVersion(new Version(1,0,9));

        setCategory("Exclusive Actions");

    }

    @Override
    public void initProperties() throws Exception {
        Property roomName = new Property("roomName", Type.STRING);
        roomName.setDisplayName("Hue Device Room Name");
        roomName.setDefaultValueStr("Bedroom");
        roomName.setCanBeBlank(false);

        addClientProperties(roomName);
    }

    @Override
    public void initAction()  {

    }


    @Override
    public void onActionClicked()
    {
        Future<List<HueBridge>> bridgesFuture = new HueBridgeDiscoveryService()
                .discoverBridges(bridge -> System.out.println("Bridge found: " + bridge));
        final List<HueBridge> bridges = bridgesFuture.get();
        if(!bridges.isEmpty())
        {
            final String bridgeIp = bridges.get(0).getIp();
            System.out.println("Bridge found at " + bridgeIp);
        }

        final String bridgeIp = bridges.get(0).getIp();
        final CompletableFuture<String> apiKey = Hue.hueBridgeConnectionBuilder(bridgeIp);

        final String key = apiKey.get();
        System.out.println("Store this API Key for future use: " + key);
        final Hue hue = new Hue(bridgeIp, key);

        Property roomName = getClientProperties().getSingleProperty("roomName");

        Room room = hue.getRoomByName(roomName.getStringValue());

        colorChosen = new ColorPicker();

        room.setState(State.builder().color(Color.of(colorChosen.visibleProperty()).on()));
    }
}