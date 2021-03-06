package com.apsent.villapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIs {
    //    #---------------------Users---------------------
    @GET("users/all")
    Call<List<Users>> getusers();

    @GET("users/findByPhone")
    Call<List<Users>> getuserBYphone(@Query("phone") String phone);

    @POST("users/add")
    Call<List<Users>> createuser(@Body Users users);

    //    #---------------------Villas---------------------
    @GET("villas/all")
    Call<List<Villa>> allVillas();


    @GET("villas/provider")
    Call<List<Villa>> getProviderVillas(@Query("PID") int PID);

    @GET("villas/delete")
    Call<Boolean> deleteVilla( @Query("id") int id);

    @POST("villas/add")
    Call<List<Villa>> createvillas(@Body Villa villa);

    @POST("villas/update")
    Call<Boolean> updateVilla(@Body Villa villa);
    /*--------------------------Reservation-------------------------------------*/
    @POST("reservation/add")
    Call<Boolean> addReservarion(@Body CustomerReservation reservation);
}
