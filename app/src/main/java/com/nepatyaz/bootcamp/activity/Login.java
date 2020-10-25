package com.nepatyaz.bootcamp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nepatyaz.bootcamp.MainActivity;
import com.nepatyaz.bootcamp.R;
import com.nepatyaz.bootcamp.model.LoginBody;
import com.nepatyaz.bootcamp.model.Result;
import com.nepatyaz.bootcamp.services.AppService;
import com.nepatyaz.bootcamp.utility.Const;
import com.nepatyaz.bootcamp.utility.interfaces.UsersApiService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private String TAG = "Login Activity";
    private Button btnLogin, btnRegister;
    private EditText inputUsername, inputPassword;
    private String userName, password;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initializeRetrofit();

    }

    private void initView() {
        //hello world 

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);

        btnLogin.setOnClickListener(v -> {
            userName = inputUsername.getText().toString();
            password = inputPassword.getText().toString();
            checkInput(userName, password);
        });

        btnRegister.setOnClickListener(v -> {
            toRegisterActivity();
        });

    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void checkInput(String userName, String password) {

        if (userName.isEmpty() || userName.equals("")) {
            Toast.makeText(this, "Please input username", Toast.LENGTH_SHORT).show();
        }

        if (password.isEmpty() || password.equals("")) {
            Toast.makeText(this, "Please input password", Toast.LENGTH_SHORT).show();
        }

        if (!password.isEmpty() && !userName.isEmpty()) {

            LoginBody loginBody = new LoginBody(userName, password);
            userLogin(loginBody);

//            toMainActivity();
        }

    }


    private void initializeRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

//        getAllUser();
    }


//    private void getAllUser() {
//
//        UsersApiService apiService = retrofit.create(UsersApiService.class);
//        Call<Result> result = apiService.getAllUsers();
//
//        result.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Log.e(TAG, "onResponse: "+ response.body() );
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.toString() );
//
//            }
//        });
//    }

    private void userLogin(LoginBody loginBody) {
        UsersApiService apiService = retrofit.create(UsersApiService.class);
        Call<Result> result = apiService.userLogin(loginBody);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                Result apiResult = response.body();
                boolean success = apiResult.getSuccess();

                if (success) {
                    AppService.setToken(apiResult.getToken());
                    toMainActivity();
                } else {
                    Toast.makeText(Login.this, apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(Login.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}