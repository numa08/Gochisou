package net.numa08.gochisou.domain.repository;

import net.numa08.gochisou.domain.model.Post;

import java.util.List;

import rx.Observable;

public interface EsaRepository {

    Observable<List<Post>> getPosts(String query);

    Observable<List<Post>> getPosts();

    Observable<Post> savePost(Post post);

    Observable<List<Post>> savePost(List<Post> posts);
}
