package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.matthewwatson.peoplemongo.Model.User;
import com.example.matthewwatson.peoplemongo.Network.RestClient;
import com.example.matthewwatson.peoplemongo.Network.UserStore;
import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Stages.MapStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class RegisterView extends LinearLayout {
    private Context context;

    @Bind(R.id.user_name_ET)
    EditText userNameField;

    @Bind(R.id.password_1_ET)
    EditText passwordField1;

    @Bind(R.id.password_confirm_ET)
    EditText passwordConfirm;

    @Bind(R.id.email_address_ET)
    EditText emailField;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register(){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userNameField.getWindowToken(),0);//used to hide keyboard after input
        imm.hideSoftInputFromWindow(passwordField1.getWindowToken(),0);
        imm.hideSoftInputFromWindow(passwordConfirm.getWindowToken(),0);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(),0);

        String username = userNameField.getText().toString();
        String password1 = passwordField1.getText().toString();
        String password2 = passwordConfirm.getText().toString();
        String email = emailField.getText().toString();

        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || email.isEmpty()){
            Toast.makeText(context, R.string.empty_username_or_password, Toast.LENGTH_LONG).show();

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, R.string.valid_email, Toast.LENGTH_LONG).show();

        }else if (!password1.equals(password2)){
            Toast.makeText(context, context.getResources().getString (R.string.passwords_dont_match), Toast.LENGTH_LONG).show();
        }
        else {
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            User user = new User(username,password1,email);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()){ //checks for 200-299
                        User regUser = response.body();
                        UserStore.getInstance().setToken(regUser.getToken());
                        UserStore.getInstance().setTokenExpiration(regUser.getExpiration());

                        Flow flow = PeoplemonApplication.getMainFlow();
                        History newHistory = History.single(new MapStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    }else {
                        resetView();
                        Toast.makeText(context, R.string.registration_failed + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, R.string.registration_failed, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void resetView(){
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }


}

