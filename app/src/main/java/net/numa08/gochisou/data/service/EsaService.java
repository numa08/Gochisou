package net.numa08.gochisou.data.service;

import net.numa08.gochisou.data.model.PageNation;
import net.numa08.gochisou.data.model.Team;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface EsaService {
    @GET("/v1/teams")
    Observable<PageNation.TeamPageNation> teams(@Header("Authorization: Bearer") String token);
}
