package com.robothijau.todolistapp

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndPoint {
    @GET("tampil.php")
    fun getAllData(): Call<TodoListResponse>

    @FormUrlEncoded
    @POST("simpan.php")
    fun addData(
        @Field("judul") judul: String,
        @Field("deskripsi") deskripsi: String,
        @Field("status") status: String,
        @Field("tanggal") tanggal: String,
    ): Call<SubmitRespon>

    @FormUrlEncoded
    @POST("edit.php")
    fun editData(
        @Field("tugas_id") tugas_id: String,
        @Field("judul") judul: String,
        @Field("deskripsi") deskripsi: String,
        @Field("status") status: String,
        @Field("tanggal") tanggal: String,
    ): Call<SubmitRespon>

    @FormUrlEncoded
    @POST("hapus.php")
    fun hapusData(
        @Field("tugas_id") tugas_id: String,
    ): Call<SubmitRespon>

}