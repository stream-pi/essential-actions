package com.stream_pi.twitteraction;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertListener;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.uihelper.HBoxInputBox;
import com.stream_pi.util.version.Version;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URI;
import java.util.Random;

public class TwitterAction extends NormalAction {

    Button loginAsNewUserButton, logoutButton;

    public TwitterAction()
    {
        setName("Tweet");
        setCategory("Twitter");
        setAuthor("rnayabed");
        setServerButtonGraphic("fab-twitter");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(1,0,1));


        loginAsNewUserButton = new Button("Login as new user");

        loginAsNewUserButton.setOnAction(event-> new Thread(new Task<Void>() {
            @Override
            protected Void call()
            {
                try {
                    Platform.runLater(()->{
                        loginAsNewUserButton.setDisable(true);
                        logoutButton.setDisable(true);
                    });

                    loginAsNewUser();
                } catch (Exception e) {
                    Platform.runLater(()-> {

                        Platform.runLater(()->{
                            loginAsNewUserButton.setDisable(false);
                            logoutButton.setDisable(false);
                        });

                        new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show();
                    });
                    e.printStackTrace();
                }
                return null;
            }
        }).start());

        logoutButton = new Button("Logout");

        logoutButton.setOnAction(event-> new Thread(new Task<Void>() {
            @Override
            protected Void call()
            {
                try {
                    Platform.runLater(()->{
                        loginAsNewUserButton.setDisable(false);
                        logoutButton.setDisable(false);
                    });

                    logout();
                } catch (Exception e) {
                    Platform.runLater(()-> {
                        Platform.runLater(()->{
                            loginAsNewUserButton.setDisable(false);
                            logoutButton.setDisable(false);
                        });

                        new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show();
                    });
                    e.printStackTrace();
                }
                return null;
            }
        }).start());

        setServerSettingsButtonBar(loginAsNewUserButton, logoutButton);
    }

    @Override
    public void initProperties() throws Exception
    {
        Property oAuthConsumerKey = new Property("consumer_key", Type.STRING);
        oAuthConsumerKey.setDisplayName("API Key");

        Property oAuthConsumerKeySecret = new Property("consumer_key_secret", Type.STRING);
        oAuthConsumerKeySecret.setControlType(ControlType.TEXT_FIELD_MASKED);
        oAuthConsumerKeySecret.setDisplayName("API Key Secret");

        Property oAuthAccessToken = new Property("access_token", Type.STRING);
        oAuthAccessToken.setDisplayName("Access Token");
        oAuthAccessToken.setVisible(false);

        Property oAuthAccessTokenSecret = new Property("access_token_secret", Type.STRING);
        oAuthAccessTokenSecret.setDisplayName("Access Token Secret");
        oAuthAccessTokenSecret.setVisible(false);

        addServerProperties(
                oAuthConsumerKey,
                oAuthConsumerKeySecret,
                oAuthAccessToken,
                oAuthAccessTokenSecret
        );



        Property toBeTweeted = new Property("tweet", Type.STRING);
        toBeTweeted.setDisplayName("Tweet");
        toBeTweeted.setDefaultValueStr("Hello From Stream-Pi!");
        toBeTweeted.setCanBeBlank(false);

        addClientProperties(
                toBeTweeted
        );

    }

    public void loginAsNewUser() throws Exception
    {
        logout();

        getAuthToken();
    }

    public void logout() throws Exception
    {
        setNewTwitterConfig(
                getServerProperties().getSingleProperty("consumer_key").getStringValue(),
                getServerProperties().getSingleProperty("consumer_key_secret").getStringValue(),
                null,null
        );
    }

    public void getAuthToken() throws Exception {
        RequestToken requestToken = tf.getInstance().getOAuthRequestToken();

        String cancel = "Cancel";
        String login = "Log In";



        TextArea authURLTextArea = new TextArea(requestToken.getAuthorizationURL());
        authURLTextArea.setEditable(false);

        TextField pinTextField = new TextField();

        HBox textBox = new HBoxInputBox("PIN", pinTextField);




        Platform.runLater(()->{
            try
            {
                StreamPiAlert alert =  new StreamPiAlert("Authorise App", StreamPiAlertType.INFORMATION, cancel, login);

                VBox vBox = new VBox(new Label("Go the following Link below, authorise app and then enter the PIN Below"), authURLTextArea, textBox);
                vBox.setSpacing(5.0);

                alert.setAlertContent(vBox);

                alert.show();

                alert.setOnClicked(new StreamPiAlertListener(){

                    @Override
                    public void onClick(String buttonClicked) {
                        // TODO Auto-generated method stub

                        try
                        {
                            if(buttonClicked.equals(login))
                        {
                            AccessToken accessToken = tf.getInstance().getOAuthAccessToken(requestToken, pinTextField.getText());

                            getServerProperties().getSingleProperty("access_token").setStringValue(accessToken.getToken());
                            getServerProperties().getSingleProperty("access_token_secret").setStringValue(accessToken.getTokenSecret());
        
                            saveServerProperties();
        
                            initAction();

                            new StreamPiAlert("Success", "Login Successful!", StreamPiAlertType.INFORMATION).show();
                        }
                        else
                        {
                            initAction();
                        }
                        }
                        catch (Exception e)
                        {
                            new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show();
                            e.printStackTrace();
                        }

                    }
                    
                });

            }
            catch (Exception e)
            {
                new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show();
                e.printStackTrace();
            }
            finally
            {
                loginAsNewUserButton.setDisable(false);
            }
        });

    }

    TwitterFactory tf;

    @Override
    public void initAction() throws Exception {
        //System.setProperty("twitter4j.http.useSSL", "true");

        setNewTwitterConfig(
                getServerProperties().getSingleProperty("consumer_key").getStringValue(),
                getServerProperties().getSingleProperty("consumer_key_secret").getStringValue(),
                getServerProperties().getSingleProperty("access_token").getStringValue(),
                getServerProperties().getSingleProperty("access_token_secret").getStringValue()
        );
    }

    public void setNewTwitterConfig(String consumerKey, String consumerKeySecret, String accessToken, String accessTokenSecret)
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerKeySecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);


        tf = new TwitterFactory(cb.build());
    }

    @Override
    public void onActionClicked() throws Exception {
        Twitter twitter = tf.getInstance();
        twitter.updateStatus(getClientProperties().getSingleProperty("tweet").getStringValue());
    }

    /*public String addRandomBlank(String value)
    {
        return value+("⠀".repeat(new Random().nextInt(100)));
    }*/

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
    
}
