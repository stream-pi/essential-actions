package com.stream_pi.spotify;

import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Spotify extends NormalAction
{

    public Spotify()
    {
        setName("Spotify");
        setAuthor("rnayabed");
        setHelpLink("https://github.com/stream-pi/");
        setVersion(new Version(1,0,0));

        setCategory("Spotify");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property clientID = new Property("clientID", Type.STRING);
        clientID.setDisplayName("Spotify Client ID");

        Property clientSecret = new Property("clientSecret", Type.STRING);
        clientSecret.setDisplayName("Spotify Client Secret");


        addClientProperties(
                clientID,
                clientSecret

        );
    }

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
        .build();
        SpotifyAPI spotifyApi1 = new SpotifyApi.Builder();
        Property id = getClientProperties().getSingleProperty("clientID");
        Property secret = getClientProperties().getSingleProperty("clientSecret")

        .setClientId(id.getStringValue())
        .setClientSecret(secret.getStringValue())
        .build();


    @Override
    public void initAction()  {

    }


    @Override
    public void onActionClicked()
    {
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        System.out.println("Action Called!");
    }
}
