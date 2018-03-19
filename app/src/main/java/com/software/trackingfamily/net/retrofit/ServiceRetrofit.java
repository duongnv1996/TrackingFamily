package com.software.trackingfamily.net.retrofit;

import com.software.trackingfamily.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by thaopt on 11/24/17.
 */

public interface ServiceRetrofit {
    @FormUrlEncoded
    @POST("/")
    Call<ApiResponse<User>> login(@Field("type") String type, @Field("phone") String phone, @Field("password") String pass);
    @FormUrlEncoded
    @POST("/")
    Call<ApiResponse<List<User>>> getMemberOfFamily(@Field("type") String type, @Field("family_id") String idFam);
    @FormUrlEncoded
    @POST("/")
    Call<ApiResponse> updateLatlng(@Field("type") String type, @Field("user_id") String idFam,@Field("last_lat") double lat,@Field("last_lng") double lng);
    @FormUrlEncoded
    @POST("/")
    Call<ApiResponse<User>> register(@Field("type") String type, @Field("phone") String phone, @Field("password") String pass, @Field("username") String username,
                                     @Field("email") String email, @Field("family") String family_id
    );


    @FormUrlEncoded
    @POST("/")
    Call<ApiResponse<User>> updateInfor(@Field("type") String type, @Field("phone") String phone, @Field("password") String pass, @Field("username") String username,
                                     @Field("email") String email, @Field("family") String family_id,@Field("user_id") String user_id
    );
}
