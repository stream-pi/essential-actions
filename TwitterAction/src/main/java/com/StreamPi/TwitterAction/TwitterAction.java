package com.StreamPi.TwitterAction;

import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Alert.StreamPiAlert;
import com.StreamPi.Util.Alert.StreamPiAlertListener;
import com.StreamPi.Util.Alert.StreamPiAlertType;
import com.StreamPi.Util.Exception.MinorException;
import com.StreamPi.Util.FormHelper.HBoxInputBox;
import com.StreamPi.Util.Version.Version;
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

    Button loginAsNewUserButton;

    public TwitterAction()
    {
        setName("Tweet");
        setCategory("Twitter");
        setAuthor("rnayabed");
        setServerButtonGraphic("fab-twitter");
        setRepo("https://github.com/Stream-Pi/EssentialAction");
        setVersion(new Version(1,0,0));


        loginAsNewUserButton = new Button("Login as new user");

        loginAsNewUserButton.setOnAction(event-> new Thread(new Task<Void>() {
            @Override
            protected Void call()
            {
                try {
                    Platform.runLater(()->loginAsNewUserButton.setDisable(true));
                    loginAsNewUser();
                } catch (Exception e) {
                    Platform.runLater(()-> {
                        loginAsNewUserButton.setDisable(false);
                        new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show();
                    });
                    e.printStackTrace();
                }
                return null;
            }
        }).start());


        setButtonBar(loginAsNewUserButton);
    }

    @Override
    public void initProperties() throws Exception
    {
        Property oAuthConsumerKey = new Property("consumer_key", Type.STRING);
        oAuthConsumerKey.setDisplayName("API Key");

        Property oAuthConsumerKeySecret = new Property("consumer_key_secret", Type.STRING);
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
        toBeTweeted.setDefaultValueStr("Hello From StreamPi!");
        toBeTweeted.setCanBeBlank(false);

        addClientProperties(
                toBeTweeted
        );

    }

    public void loginAsNewUser() throws Exception
    {
        setNewTwitterConfig(
                getServerProperties().getSingleProperty("consumer_key").getStringValue(),
                getServerProperties().getSingleProperty("consumer_key_secret").getStringValue(),
                null,null
        );

        getAuthToken();
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
