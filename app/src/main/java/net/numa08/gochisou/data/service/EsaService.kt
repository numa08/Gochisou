package net.numa08.gochisou.data.service

import net.numa08.gochisou.data.model.PageNation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface EsaService {
    @GET("/v1/teams")
    fun teams(@Header("Authorization") token: String?): Call<PageNation.TeamPageNation>

    @GET("/v1/teams/{team_name}/posts")
    fun posts(@Header("Authorization") token: String?, @Path("team_name") teamName: String?): Call<PageNation.PostPageNation>
}
