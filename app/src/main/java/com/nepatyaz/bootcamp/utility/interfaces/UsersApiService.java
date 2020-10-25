package com.nepatyaz.bootcamp.utility.interfaces;

import com.nepatyaz.bootcamp.model.LoginBody;
import com.nepatyaz.bootcamp.model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsersApiService {

    @GET("api/users/getAll")
    Call<Result> getAllUsers();

    @POST("login")
    Call<Result> userLogin(@Body LoginBody loginBody);

}
