package com.stream_pi.phillipshue.changebrightness;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;

import io.github.zeroone3010.yetanotherhueaapi.*;
import io.github.zeroone3010.yetanotherhueapi.discovery.*;

import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

import java.io.List;

public class ChangeBrightness extends NormalAction
{

    public ChangeBrightness()
    {
        setName("Phillips Hue - Change Brightness");
        setAuthor("quimodotcom");
        setHelpLink("https://github.com/quimodotcom/");
        setVersion(new Version(1,0,0));

        setCategory("Exclusive Actions");

    }

    @Override
    public void initProperties() throws Exception {
        Property roomName = new Property("roomName", Type.STRING);
        roomName.setDisplayName("Hue Device Room Name");
        roomName.setDefaultValueStr("Bedroom");
        roomName.setCanBeBlank(false);

        Property brightnessLevel = new Property("brightnessLevel", Type.STRING);
        brightnessLevel.setDisplayName("Hue Device Brightness Level");
        brightnessLevel.setDefaultValueStr("10");
        brightnessLevel.setCanBeBlank(false);

        addClientProperties(roomName, brightnessLevel);
    }

    @Override
    public void initAction()  {

    }


    @Override
    public void onActionClicked() throws Exception
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

        Property brightnessLevel = getClientProperties().getSingleProperty("brightnessLevel");

        Room room = hue.getRoomByName(brightnessLevel.getStringValue());

        room.setBrightness(brightnessLevel.getStringValue());
    }
}