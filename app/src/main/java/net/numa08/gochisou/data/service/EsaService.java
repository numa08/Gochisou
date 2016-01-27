package net.numa08.gochisou.data.service;

import net.numa08.gochisou.domain.model.Post;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface EsaService {

    @GET("/v1/teams/docs/posts")
    Observable<List<Post>> posts(@Query("q") String query);

    @POST("/v1/teams/docs/posts")
    Observable<Post> post(Post post);
}
