package com.jndcb.jndcbtwitter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jndcb.jndcbtwitter.Controller.SearchUser;
import com.jndcb.jndcbtwitter.Utils.Util;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    EditText editTextSearch;
    Button buttonSearch;
    ImageView imageViewTwitter;

    /**
     * Retrieve global application context.
     *
     * @return
     */
    public static Context getGlobalContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        editTextSearch = findViewById(R.id.text_field);
        buttonSearch = findViewById(R.id.button);
        imageViewTwitter = findViewById(R.id.image_twitter);


        buttonSearch.setBackgroundColor(getResources().getColor(R.color.twitterButton));
        editTextSearch.setHorizontallyScrolling(false);
        editTextSearch.setMaxLines(1);
        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard();
                }
            }
        });
    }

    public void CallFragment(View view) {
        String getText = editTextSearch.getText().toString();
        if (!getText.isEmpty()) {
            new SearchUser(MainActivity.this).execute(getText);
        }
        else {
            new Util().AlertMessage(getResources().getString(R.string.edittext_empty_field), context);
        }
    }

    public void loadFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = this.getSupportFragmentManager();
        if(fm.getBackStackEntryCount()>0) {
            fm.popBackStack();
        }
        else {
            new Util().AlertMessageTwoButtons(getResources().getString(R.string.exit_app), context);
        }
    }

    // https://stackoverflow.com/questions/20713273/dismiss-keyboard-when-click-outside-of-edittext-in-android
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scRecords[] = new int[2];
            v.getLocationOnScreen(scRecords);
            float x = ev.getRawX() + v.getLeft() - scRecords[0];
            float y = ev.getRawY() + v.getTop() - scRecords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard();
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard() {
        InputMethodManager input = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (input != null) {
            input.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
            findViewById(android.R.id.content).clearFocus();
        }
    }
}
