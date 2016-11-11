package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.matthewwatson.peoplemongo.Componets.Constants;
import com.example.matthewwatson.peoplemongo.Model.Authorization;
import com.example.matthewwatson.peoplemongo.Network.RestClient;
import com.example.matthewwatson.peoplemongo.Network.UserStore;
import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Stages.MapStage;
import com.example.matthewwatson.peoplemongo.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.matthewwatson.peoplemongo.PeoplemonApplication.getMainFlow;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.login_user_name_ET)
    EditText userNameField;

    @Bind(R.id.login_password_ET)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.login_register_button)
    Button loginRegisterButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;


    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() { //inflates our container
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_register_button)
    public void showRegisterView() {
        Flow flow = getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }

    @OnClick(R.id.login_button)
    public void login() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userNameField.getWindowToken(), 0);//used to hide keyboard after input
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);//terrible.

        String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String grantType = "password";


        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.empty_username_or_password, Toast.LENGTH_LONG).show();
        } else {
            loginButton.setEnabled(false);
            loginRegisterButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            RestClient restClient = new RestClient();
            restClient.getApiService().login(Constants.grant_type, username, password).enqueue(new Callback<Authorization>() {

                @Override
                public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                    if (response.isSuccessful()) { //checks for 200-299
                        Authorization authUser = response.body();//gets user
                        UserStore.getInstance().setToken(authUser.getToken());//gets token
                        UserStore.getInstance().setTokenExpiration(authUser.getExpiration());//gets token exp

                        Flow flow = PeoplemonApplication.getMainFlow();
                        History newHistory = History.single(new MapStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    } else {
                        resetView();
                        Toast.makeText(context, context.getResources().getString(R.string.login_failed) + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Authorization> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void resetView() {
        loginButton.setEnabled(true);
        loginRegisterButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}

