package net.numa08.gochisou.data.service

import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.model.Token
import net.numa08.gochisou.data.model.TokenInfo
import retrofit2.Call
import retrofit2.http.*

interface EsaService {
    @GET("/v1/teams")
    fun teams(@Header("Authorization") token: String?): Call<PageNation.TeamPageNation>

    @GET("/v1/teams/{team_name}/posts")
    fun posts(@Header("Authorization") token: String?, @Path("team_name") teamName: String?, @Query("q") query: String? = null): Call<PageNation.PostPageNation>

    @GET("/v1/teams/{team_name}/members")
    fun members(@Header("Authorization") token: String?, @Path("team_name") teamName: String?): Call<PageNation.MemberPageNation>

    @POST("/oauth/token")
    fun token(@Field("client_id") clientId: String, @Field("client_secret") clientSecret: String, @Field("grant_type") grantType: String = "authorization_code", @Field("redirect_uri") redirectURL: String, @Field("code") code: String): Call<Token>

    @GET("/oauth/token/info")
    fun tokenInfo(@Header("Authorization") token: String): Call<TokenInfo>
}
