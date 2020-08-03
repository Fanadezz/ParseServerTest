package com.androidshowtime.parseservertest;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener,
        View.OnClickListener {

    InputMethodManager inputMethodManager;
    private ParseUser user;
    private EditText usernameEd, passwordEd;
    private boolean signUpMode = true;
    private Button signUpButton;
    private TextView loginTextView;
    private boolean credentialsAreBlank = true;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Instagram");
        Timber.plant(new Timber.DebugTree());
        inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        user = new ParseUser();

        usernameEd = findViewById(R.id.usernameEd);
        passwordEd = findViewById(R.id.passwordEd);
        signUpButton = findViewById(R.id.signUpButton);
        loginTextView = findViewById(R.id.loginTextView);
        constraintLayout = findViewById(R.id.layout);


        passwordEd.setOnKeyListener(this);
        constraintLayout.setOnClickListener(this);


        //helps to see how much the user is using our app
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    public void onClickButton(View view) {

        userNameAndPasswordChecker(usernameEd.getText().toString(), passwordEd.getText()
                                                                              .toString());

        if (signUpMode && !credentialsAreBlank) {


            signUpCall();

            signUpMode = false;
            signUpButton.setText("or Login");
            loginTextView.setText("or Sign Up");
        } else {
            loginCall();
            signUpMode = true;
            signUpButton.setText("or Sign Up");
            loginTextView.setText("or Login");
        }


        //you need to specify the List type i.e. integer
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(2);
        list.add(3);
        list.remove(1);
        System.out.println(list.get(1));


    }


    public void onClickTextView(View view) {
        userNameAndPasswordChecker(usernameEd.getText().toString(), passwordEd.getText()
                                                                              .toString());

        if (!signUpMode && !credentialsAreBlank) {

            signUpCall();

            signUpMode = true;
            loginTextView.setText("or Login");
            signUpButton.setText("or Sign Up");

        } else {


            loginCall();


            signUpMode = false;
            loginTextView.setText("or Sign Up");
            signUpButton.setText("or Login");
        }


    }

    private void signUpCall() {
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                //setting up users credentials
                user.setUsername(usernameEd.getText().toString());
                user.setPassword(passwordEd.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {
                            Timber.i("User signed up successfully");

                            navigatetoUsersList();
                        } else {

                            makeText(MainActivity.this, e.getMessage(), LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }


    private void loginCall() {
        ParseUser.logInInBackground(usernameEd.getText().toString(), passwordEd.getText()
                                                                               .toString(),
                                    new LogInCallback() {
                                        @Override
                                        public void done(ParseUser user, ParseException e) {
                                            if (e == null) {

                                                makeText(MainActivity.this, "Successful Login",
                                                         LENGTH_SHORT)
                                                        .show();

                                                navigatetoUsersList();

                                            } else {

                                                makeText(MainActivity.this, e.getMessage(),
                                                         LENGTH_SHORT).show();
                                            }
                                        }
                                    });
    }


    public void userNameAndPasswordChecker(String username, String password) {

        if (username.matches("") || password.matches("")) {

            makeText(this, "Username and Password required", LENGTH_SHORT).show();
        } else {

            credentialsAreBlank = false;
        }


    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        //keycode tells us what kind of key event was pressed

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            onClickTextView(v);
        }


        return false;
    }

    @Override
    public void onClick(View v) {


        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, 0);

        //inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus())
        // .getWindowToken(), 0);

    }


    private void navigatetoUsersList() {


        startActivity(new Intent(MainActivity.this, UsersList.class));
    }


}






/* //add a new ParseObject which is like a field in a table - the object takes a class name
        // which the first letter is uppercase
        ParseObject score = new ParseObject("Score");

        //adding properties to the score object

        score.put("Username", "Xavi");
        score.put("Score", 13);

        //saving the object in the background to prevent blocking Main Thread

        //the SaveCallBack is showing the status of the save operation
        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                //the procedure was successful
                if (e == null) {
                    Timber.i("Save Operation Successful!");

                    //something went wrong
                } else {

                    e.printStackTrace();
                }
            }
        });*/




/* //getting an object using ParseQuery which takes a classname

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");

        //get the query processed in the background using the object Id and GetCallBack
        query.getInBackground("8Va7rS8MP2", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {

                    //updating the value of object locally
                    object.put("Score", 110);

                    //saving the value which will also reflect in the browser
                    object.saveInBackground();
                    Timber.i("the username is: %s", object.getString("Username"));
                    Timber.i("the score is: %d", object.getInt("Score"));
                } else {

                    e.printStackTrace();
                }
            }
        });*/

/*ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Tweets");

       query.getInBackground("GX0nEpuS1b", new GetCallback<ParseObject>() {
           @Override
           public void done(ParseObject object, ParseException e) {

               if (e == null && object != null) {

                   object.put("user", "GOAT_Himself");
                   object.put("tweet", " I prefer Kotlin instead");

                   object.saveInBackground();


                   Timber.i("Tweeter is %s while the tweet is: %s", object.getString("user"),
                            object.getString("tweet"));

               } else {

                   e.printStackTrace();
               }


           }
       });*/


  /* ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");

//look for Username values of "GOAT"
// query.whereEqualTo("Username", "GOAT");
//set results limit

        query.whereGreaterThan("Score", 50);


                //finding all entries using findInBackground
                query.findInBackground(new FindCallback<ParseObject>() {
@Override
public void done(List<ParseObject> objects, ParseException e) {

        if (e == null && objects.size()>0) {


        for (ParseObject object: objects){


        int newValue = object.getInt("Score") + 20;
        object.put("Score", newValue);
        object.saveInBackground();

        Timber.i("Name: %s Score: %d", object.getString("Username"), object.getInt("Score"));
        }

        } else {

        e.printStackTrace();
        }
        }
        });
*/







  /*//creating a user

        ParseUser user = new ParseUser();


        //setting up user's credentials
        user.setUsername("Kago");
        user.setPassword("mypassword");

        //signUp callback
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) {
                    Timber.i("Signup completed successfull");
                } else {

                    e.printStackTrace();
                }
            }
        });*/

/*/
 */







/*

//login

        ParseUser.logInInBackground("Kago", "mypassword", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user!= null) {

                    Timber.i("We logged in");
                } else {


                    e.printStackTrace();
                }
            }
        });
*/



/*  //logging out

        ParseUser.logOut();*/






        /*//checking if there is a user currently logged

        if (ParseUser.getCurrentUser() != null) {
            Timber.i("Current user %s", ParseUser.getCurrentUser().getUsername());
        } else Timber.i("Login Functionality should be triggered");
*/