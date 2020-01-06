package com.jndcb.jndcbtwitter.Model;

import com.jndcb.jndcbtwitter.MainActivity;
import com.jndcb.jndcbtwitter.R;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;


/*  Pick one of the libraries here: https://developer.twitter.com/en/docs/developer-utilities/twitter-libraries
    http://twitter4j.org/javadoc/index.html
    http://twitter4j.org/en/code-examples.html*/

public class TwitterFactory {

    public QueryResult getTwitter(ConfigurationBuilder builder, String... strings) throws TwitterException {

        // OAuth2
        OAuth2Token token = new twitter4j.TwitterFactory(builder.build()).getInstance().getOAuth2Token();

        builder = new ConfigurationBuilder();
        builder.setApplicationOnlyAuthEnabled(true);
        builder.setOAuthConsumerKey(MainActivity.getGlobalContext().getResources().getString(R.string.api_key));
        builder.setOAuthConsumerSecret(MainActivity.getGlobalContext().getResources().getString(R.string.api_secret_key));
        builder.setOAuth2TokenType(token.getTokenType());
        builder.setOAuth2AccessToken(token.getAccessToken());

        Twitter twitter = new twitter4j.TwitterFactory(builder.build()).getInstance();

        // Get query values
        Query query = new Query(strings[0]);
        // YOu can set the count of maximum records here
        query.count(100);
        QueryResult result;
        result = twitter.search(query);

        return result;
    }


}
