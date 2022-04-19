package com.stream_pi.webhookaction;

import com.stream_pi.action_api.actionproperty.ClientProperties;
import com.stream_pi.action_api.actionproperty.property.*;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.version.Version;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WebhookAction extends NormalAction
{
    public WebhookAction()
    {
        setName("Webhook");
        setCategory("Essentials");
        setAuthor("rnayabed");
        setServerButtonGraphic("fas-cloud-upload-alt");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(2,0,0));
    }

    @Override
    public void initProperties() throws MinorException
    {
        StringProperty url = new StringProperty("url");
        url.setDisplayName("Webhook URL");

        ListProperty requestMethod = new ListProperty("request_method");
        requestMethod.setDisplayName("Request Method");
        requestMethod.setValue(List.of(
                new ListValue(RequestMethod.POST, RequestMethod.POST.getDisplayName()),
                new ListValue(RequestMethod.GET, RequestMethod.GET.getDisplayName())
        ));

        BooleanProperty useInsecureConnection = new BooleanProperty("use_insecure_connection");
        useInsecureConnection.setDisplayName("Use insecure connection");
        useInsecureConnection.setDefaultValueBoolean(false);

        BooleanProperty ignoreErrorResponse = new BooleanProperty("ignore_error_response");
        ignoreErrorResponse.setDisplayName("Ignore error response");

        StringProperty payload = new StringProperty("payload");
        payload.setDisplayName("Payload");

        StringProperty contentType = new StringProperty("content_type");
        contentType.setDisplayName("Content Type");

        StringProperty userAgent = new StringProperty("user_agent");
        userAgent.setDisplayName("User Agent");
        userAgent.setDefaultValue("Stream-Pi Webhook");


        addClientProperties(url, requestMethod, useInsecureConnection, ignoreErrorResponse, payload, contentType, userAgent);
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        try
        {
            ClientProperties clientProperties = getClientProperties();
            executeWebhook(
                    clientProperties.getSingleProperty("url").getStringValue(),
                    (RequestMethod) clientProperties.getSingleProperty("request_method").getSelectedListValue().getName(),
                    clientProperties.getSingleProperty("use_insecure_connection").getBoolValue(),
                    clientProperties.getSingleProperty("ignore_error_response").getBoolValue(),
                    clientProperties.getSingleProperty("payload").getStringValue(),
                    clientProperties.getSingleProperty("content_type").getStringValue(),
                    clientProperties.getSingleProperty("user_agent").getStringValue()
            );
        }
        catch (IOException e)
        {
            throwMinorException(e.getMessage());
        }
    }


    private enum RequestMethod
    {
        GET("GET"), POST("POST");

        private final String displayName;
        RequestMethod(String displayName)
        {
            this.displayName = displayName;
        }

        public String getDisplayName()
        {
            return displayName;
        }
    }

    private void executeWebhook(String url,
                                RequestMethod requestMethod,
                                boolean useInsecureConnection,
                                boolean ignoreErrorResponse,
                                String payload,
                                String contentType,
                                String userAgent)
            throws IOException
    {
        HttpURLConnection insecureConnection = null;
        HttpsURLConnection secureConnection = null;

        if (useInsecureConnection)
        {
            insecureConnection = (HttpURLConnection) new URL(url).openConnection();
        }
        else
        {
            secureConnection = (HttpsURLConnection) new URL(url).openConnection();
        }

        (useInsecureConnection ? insecureConnection : secureConnection).addRequestProperty("Content-Type", contentType);
        (useInsecureConnection ? insecureConnection : secureConnection).addRequestProperty("User-Agent", userAgent);


        if(requestMethod == RequestMethod.GET)
        {
            (useInsecureConnection ? insecureConnection : secureConnection).setRequestMethod("GET");
            (useInsecureConnection ? insecureConnection : secureConnection).setDoOutput(false);
        }
        else
        {
            (useInsecureConnection ? insecureConnection : secureConnection).setRequestMethod("POST");
            (useInsecureConnection ? insecureConnection : secureConnection).setDoOutput(true);
        }

        OutputStream stream = (useInsecureConnection ? insecureConnection : secureConnection).getOutputStream();
        stream.write(payload.getBytes());
        stream.flush();

        stream.close();

        int responseCode = (useInsecureConnection ? insecureConnection : secureConnection).getResponseCode();

        if (responseCode >= 400 && !ignoreErrorResponse)
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((useInsecureConnection ? insecureConnection : secureConnection).getErrorStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null)
            {
                response.append(inputLine);
            }

            bufferedReader.close();

            throwMinorException(new MinorException(
                    "Error "+responseCode,
                    response.toString()
            ));
        }

    }
}
