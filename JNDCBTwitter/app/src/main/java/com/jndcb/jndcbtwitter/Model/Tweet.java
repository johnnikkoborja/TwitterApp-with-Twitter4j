package com.jndcb.jndcbtwitter.Model;

public class Tweet {

    String tweetBy;
    String tweetText;

    public Tweet(String tweetBy, String tweet) {
        this.tweetBy = tweetBy;
        this.tweetText = tweet;
    }

    public String getTweetBy() {
        return tweetBy;
    }

    public void setTweetBy(String tweetBy) {
        this.tweetBy = tweetBy;
    }

    public String getTweet() {
        return tweetText;
    }

    public void setTweet(String tweet) {
        this.tweetText = tweet;
    }
}
