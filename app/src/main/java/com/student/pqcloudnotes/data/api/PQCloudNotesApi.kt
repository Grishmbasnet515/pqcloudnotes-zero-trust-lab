package com.student.pqcloudnotes.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PQCloudNotesApi {
    @POST("auth/register")
    suspend fun register(@Body body: AuthRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body body: AuthRequest): AuthResponse

    @POST("auth/refresh")
    suspend fun refresh(@Body body: Map<String, String>): AuthResponse

    @GET("notes")
    suspend fun listNotes(): List<NoteResponse>

    @GET("notes/{id}")
    suspend fun getNote(@Path("id") id: String): NoteResponse

    @POST("notes")
    suspend fun upsertNote(@Body body: NoteUpsertRequest): NoteResponse

    @GET("security/events")
    suspend fun securityEvents(): List<SecurityEventResponse>
}
