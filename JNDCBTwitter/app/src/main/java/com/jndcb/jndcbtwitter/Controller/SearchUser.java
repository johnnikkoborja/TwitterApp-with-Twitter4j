package com.jndcb.jndcbtwitter.Controller;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jndcb.jndcbtwitter.MainActivity;
import com.jndcb.jndcbtwitter.Model.Tweet;
import com.jndcb.jndcbtwitter.R;
import com.jndcb.jndcbtwitter.Ui.TwitterListFragment;

import java.util.ArrayList;
import java.util.List;

import twitter4j.QueryResult;
import twitter4j.conf.ConfigurationBuilder;

import static android.content.ContentValues.TAG;

/*    Pick one of the libraries here: https://developer.twitter.com/en/docs/developer-utilities/twitter-libraries
    get some of ideas in this site: https://stackoverflow.com/questions/24995802/fetching-tweets-from-twitter-using-json-in-api-v1-1*/

public class SearchUser extends AsyncTask<String, Void, Integer> {

    private ArrayList<Tweet> tweetsList;
    private final int pass = 0;
    private final int fail = pass + 1;
    private ProgressDialog dialog;
    MainActivity mainActivity;



    public SearchUser(MainActivity main) {
        mainActivity = main;
        dialog = new ProgressDialog(main);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {

      /*get some ideas in this site:
      https://stackoverflow.com/questions/11505862/twitter-integrationconsumer-key-secret-pair-already-set
      https://stackoverflow.com/questions/20638190/twitter4j-authentication-credentials-are-missing*/

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(MainActivity.getGlobalContext().getResources().getString(R.string.api_key));
        cb.setOAuthConsumerSecret(MainActivity.getGlobalContext().getResources().getString(R.string.api_secret_key));

        // OAuth2 and Twitter Factory
        com.jndcb.jndcbtwitter.Model.TwitterFactory twitterFactory = new com.jndcb.jndcbtwitter.Model.TwitterFactory();
        // Get Query Result
        QueryResult queryResult = twitterFactory.getTwitter(cb, strings);

        List<twitter4j.Status> tweetList = null;
        if (queryResult != null) {
            tweetList = queryResult.getTweets();
        }

        StringBuilder str = new StringBuilder();
        if (tweetList != null) {
            this.tweetsList = new ArrayList<>();
            for (twitter4j.Status tweet : tweetList) {
                str.append("@").append(tweet.getUser().getScreenName()).append(" - ").append(tweet.getText()).append("\n");
                this.tweetsList.add(new Tweet("@" + tweet.getUser().getScreenName(), tweet.getText()));
            }
            return pass;
        }
        } catch (Exception e) {
            Log.d("SearchUser", e.fillInStackTrace().toString());
        }
        return fail;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // dialog show
        dialog = ProgressDialog.show(MainActivity.getGlobalContext(), "", MainActivity.getGlobalContext().getString(R.string.searching_text));
    }

    @Override
    protected void onPostExecute(Integer integer) {
        // dialog hide
        dialog.dismiss();

        if (integer == pass) {

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.getGlobalContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();

            String json = gson.toJson(tweetsList);

            editor.putString(TAG, json);
            editor.apply();

            mainActivity.loadFragment(new TwitterListFragment());

        } else {
            Toast.makeText(MainActivity.getGlobalContext(), MainActivity.getGlobalContext().getString(R.string.error), Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(integer);
    }
}
