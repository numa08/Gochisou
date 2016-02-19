package net.numa08.gochisou.data.service

import net.numa08.gochisou.data.model.PageNation
import retrofit2.http.GET
import retrofit2.http.Header
import rx.Observable

interface EsaService {
    @GET("/v1/teams")
    fun teams(@Header("Authorization: Bearer") token: String): Observable<PageNation.TeamPageNation>
}
