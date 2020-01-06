package com.jndcb.jndcbtwitter.Model;

import java.util.ArrayList;

public class TweetList {

    ArrayList<Tweet> tweetListText;

    public TweetList() {
    }

    public ArrayList<Tweet> getTweetList() {
        return tweetListText;
    }

    public void setTweetList(ArrayList<Tweet> tweetList) {
        this.tweetListText = tweetList;
    }

}
