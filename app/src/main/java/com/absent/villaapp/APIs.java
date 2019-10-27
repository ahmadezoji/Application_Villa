package com.absent.villaapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIs {
    //    #---------------------Users---------------------
    @GET("users/all")
    Call<List<Users>> getusers();

    @POST("users/add")
    Call<List<Users>> createuser(@Body Users users);

    //    #---------------------Villas---------------------
    @GET("villas/all")
    Call<List<Villa>> allVillas();


    @GET("villas/provider")
    Call<List<Villa>> getProviderVillas( @Query("PID") int PID);

    @GET("villas/delete")
    Call<Boolean> deleteVilla( @Query("id") int id);

    @POST("villas/add")
    Call<List<Villa>> createvillas(@Body Villa villa);

//    @GET("villas/update")
//    Call<Boolean> updateVilla(@Query("title") String title,@Query("address") String address,
//                              @Query("lat") Float lat,@Query("lon") Float lon,
//                              @Query("cost") Integer cost,@Query("roomcnt") Integer roomcnt,
//                              @Query("capacity") Integer capacity,@Query("area") Integer area,
//                              @Query("cover") byte[] cover,@Query("providerid") Integer providerid,
//                              @Query("id") Integer id);

    @POST("villas/update")
    Call<Boolean> updateVilla(@Body Villa villa);
}
