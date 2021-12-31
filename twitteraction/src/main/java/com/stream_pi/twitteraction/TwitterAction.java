package com.stream_pi.twitteraction;

import com.stream_pi.action_api.actionproperty.property.ControlType;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.action_api.externalplugin.NormalAction;
import com.stream_pi.util.alert.StreamPiAlert;
import com.stream_pi.util.alert.StreamPiAlertButton;
import com.stream_pi.util.alert.StreamPiAlertListener;
import com.stream_pi.util.alert.StreamPiAlertType;
import com.stream_pi.util.exception.MinorException;
import com.stream_pi.util.uihelper.HBoxInputBox;
import com.stream_pi.util.version.Version;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterAction extends NormalAction
{

    Button loginAsNewUserButton, logoutButton;

    Label currentUser;

    public TwitterAction()
    {
        setName("Tweet");
        setCategory("Twitter");
        setAuthor("rnayabed");
        setServerButtonGraphic("fab-twitter");
        setHelpLink("https://github.com/stream-pi/essentialactions");
        setVersion(new Version(2,0,0));


        currentUser = new Label();

        loginAsNewUserButton = new Button("Login as new user");

        loginAsNewUserButton.setOnAction(event-> new Thread(new Task<Void>() {
            @Override
            protected Void call()
            {
                try
                {
                    saveServerPropertiesProvidedByUser();

                    if (getServerProperties().getSingleProperty("consumer_key").getStringValue().isBlank() ||
                    getServerProperties().getSingleProperty("consumer_key_secret").getStringValue().isBlank())
                    {
                        throw new MinorException("Please provide a API Key & API Key Secret before proceeding!");
                    }
                    else
                    {
                        loginAsNewUser();
                    }
                }
                catch (Exception e)
                {
                    Platform.runLater(()->{
                        new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show();
                        loginAsNewUserButton.setDisable(false);
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
                try
                {
                    logout();
                }
                catch (Exception e)
                {
                    Platform.runLater(()-> new StreamPiAlert(e.getMessage(), StreamPiAlertType.ERROR).show());
                    e.printStackTrace();
                }
                return null;
            }
        }).start());

        setServerSettingsNodes(currentUser);
        setServerSettingsButtonBar(loginAsNewUserButton, logoutButton);
    }

    @Override
    public void initProperties() throws MinorException
    {
        Property oAuthConsumerKey = new Property("consumer_key", Type.STRING);
        oAuthConsumerKey.setDisplayName("API Key");

        Property oAuthConsumerKeySecret = new Property("consumer_key_secret", Type.STRING);
        oAuthConsumerKeySecret.setControlType(ControlType.TEXT_FIELD_MASKED);
        oAuthConsumerKeySecret.setDisplayName("API Key Secret");

        Property oAuthAccessToken = new Property("access_token", Type.STRING);
        oAuthAccessToken.setVisible(false);

        Property oAuthAccessTokenSecret = new Property("access_token_secret", Type.STRING);
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
        Platform.runLater(()-> loginAsNewUserButton.setDisable(true));
        getAuthToken();
    }

    public void logout() throws Exception
    {
        getServerProperties().getSingleProperty("access_token").setStringValue(null);
        getServerProperties().getSingleProperty("access_token_secret").setStringValue(null);

        saveServerProperties();

        initAction();
    }

    public void getAuthToken() throws Exception
    {
        RequestToken requestToken = tf.getInstance().getOAuthRequestToken();

        StreamPiAlertButton cancel = new StreamPiAlertButton("Cancel");
        StreamPiAlertButton login = new StreamPiAlertButton("Log In");



        TextArea authURLTextArea = new TextArea(requestToken.getAuthorizationURL());
        authURLTextArea.setEditable(false);

        TextField pinTextField = new TextField();

        HBox textBox = new HBoxInputBox("PIN", pinTextField);



        Platform.runLater(()->{
            try
            {
                StreamPiAlert alert =  new StreamPiAlert("Authorise App", StreamPiAlertType.INFORMATION, cancel, login);

                getHostServices().showDocument(requestToken.getAuthorizationURL());

                Label l = new Label("If below link does not open automatically, go to the link, authorise app and then enter the PIN below");
                l.setWrapText(true);

                authURLTextArea.setPrefHeight(Double.NEGATIVE_INFINITY);

                VBox vBox = new VBox(l, authURLTextArea, textBox);
                vBox.setSpacing(5.0);

                alert.setAlertContent(vBox);

                alert.show();

                alert.setOnClicked(new StreamPiAlertListener()
                {
                    @Override
                    public void onClick(StreamPiAlertButton buttonClicked)
                    {
                        try
                        {
                            if(buttonClicked.equals(login))
                            {
                                submitToExecutorService(()->{

                                    try {
                                        AccessToken accessToken = tf.getInstance().getOAuthAccessToken(requestToken, pinTextField.getText());

                                        getServerProperties().getSingleProperty("access_token").setStringValue(accessToken.getToken());
                                        getServerProperties().getSingleProperty("access_token_secret").setStringValue(accessToken.getTokenSecret());

                                        saveServerProperties();

                                        initAction();

                                        new StreamPiAlert("Success", "Login Successful!", StreamPiAlertType.INFORMATION).show();
                                    }
                                    catch (Exception e)
                                    {
                                        throwMinorException(e.getMessage());
                                    }
                                });
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
        });

    }

    TwitterFactory tf;

    @Override
    public void initAction() throws MinorException
    {
        setNewTwitterConfig(
                getServerProperties().getSingleProperty("consumer_key").getStringValue(),
                getServerProperties().getSingleProperty("consumer_key_secret").getStringValue(),
                getServerProperties().getSingleProperty("access_token").getStringValue(),
                getServerProperties().getSingleProperty("access_token_secret").getStringValue()
        );
    }

    @Override
    public void onServerPropertiesSavedByUser() throws MinorException
    {
        initAction();
    }

    public void setNewTwitterConfig(String consumerKey, String consumerKeySecret, String accessToken, String accessTokenSecret) throws MinorException
    {
        try
        {
            Platform.runLater(()->{
                logoutButton.setDisable(true);
                loginAsNewUserButton.setDisable(true);
            });

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(consumerKey)
                    .setOAuthConsumerSecret(consumerKeySecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);


            tf = new TwitterFactory(cb.build());


            submitToExecutorService(()->{
                try
                {
                    if (accessTokenSecret == null || accessTokenSecret.isBlank())
                    {
                        Platform.runLater(()->{
                            currentUser.setText(null);

                            logoutButton.setDisable(true);
                            loginAsNewUserButton.setDisable(false);
                        });
                    }
                    else
                    {
                        Platform.runLater(()->{
                            logoutButton.setDisable(false);
                            loginAsNewUserButton.setDisable(true);

                            currentUser.setText("Loading user details ...");
                        });

                        User user = tf.getInstance().showUser(tf.getInstance().getId());
                        Platform.runLater(()->currentUser.setText("Logged in as "+user.getName()+" (@"+user.getScreenName()+")"));
                    }
                }
                catch (TwitterException e)
                {
                    Platform.runLater(()-> currentUser.setText(null));
                    throwMinorException(e.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            throw new MinorException(e.getMessage());
        }
    }

    @Override
    public void onActionClicked() throws MinorException
    {
        Twitter twitter = tf.getInstance();
        try
        {
            twitter.updateStatus(
                    getClientProperties().getSingleProperty("tweet").getStringValue()
            );
        } catch (TwitterException e)
        {
            e.printStackTrace();
            throw new MinorException("Unable to update Twitter : "+e.getMessage());
        }
    }

    /*
    TOP SECRET - If you found this then congrats :D (you probably know why)
    public String getRandomBlanks()
    {
        return "⠀".repeat(new Random().nextInt(100));
    }*/
}
