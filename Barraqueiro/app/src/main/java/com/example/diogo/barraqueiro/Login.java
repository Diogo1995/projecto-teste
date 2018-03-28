package com.example.diogo.barraqueiro;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class Login extends AppCompatActivity  {
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo:hello", "bar:world"
    };
    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView usernameView;
    private EditText passwordView;

    SharedPreferences loginCredentials;

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String LOGIN_CREDENTIALS = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get login credentials
        loginCredentials = getSharedPreferences(LOGIN_CREDENTIALS, MODE_PRIVATE);
        // TODO loginar sozinho - mas só se existir coisas lá guardadas, sernão ir buscar aos campos de texto
        // String username = getString("Username", null);
        // String password = getString("Password", null);
        // mAuthTask = new userLoginTask() ? com ^
        // mAuthTask.execute((Void) null);

        // Makes the textviews go places
        TextView recoverPassword = (TextView) findViewById(R.id.recoverPassword);
        recoverPassword.setMovementMethod(LinkMovementMethod.getInstance());
        TextView siteBarraqueiro = (TextView) findViewById(R.id.siteBarraqueiro);
        siteBarraqueiro.setMovementMethod(LinkMovementMethod.getInstance());

        // Set up the login form.
        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.loginButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        usernameView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            usernameView.setError(getString(R.string.error_invalid_username));
            focusView = usernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Intent intent = new Intent(this, Menu.class);

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(username, password, intent);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return true;
        //return username.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String mUsername;
        private final String mPassword;
        private final Intent mIntent;

        UserLoginTask(String username, String password, Intent intent) {
            mUsername = username;
            mPassword = password;
            mIntent = intent;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //TODO chamar loginPost!

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mUsername)) {
                    if(pieces[1].equals(mPassword)){
                        // Save the login credentials
                        SharedPreferences.Editor editor = loginCredentials.edit();
                        editor.putString("Username", mUsername);
                        editor.putString("Password", mPassword);
                        editor.commit();

                        // Start new activity
                        startActivity(mIntent);

                        // slide_in = novo layout a aparecer na direcção indicada, slide_out = antigo layout a sair na direcção indicada
                        // Login animation
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        return true;
                    }else{
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if(!success){
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }


        public void loginPost(View view){
            String username = usernameView.getText().toString();
            String password = passwordView.getText().toString();
            //new SigninActivity(this).execute(username,password); TODO this = context!!
        }
    }
}