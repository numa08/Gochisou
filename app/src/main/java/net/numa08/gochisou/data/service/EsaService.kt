package net.numa08.gochisou.data.service

import net.numa08.gochisou.data.model.PageNation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface EsaService {
    @GET("/v1/teams")
    fun teams(@Header("Authorization: Bearer") token: String): Call<PageNation.TeamPageNation>
}
