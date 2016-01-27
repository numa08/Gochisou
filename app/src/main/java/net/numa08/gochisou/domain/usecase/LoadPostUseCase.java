package net.numa08.gochisou.domain.usecase;

import net.numa08.gochisou.domain.model.Post;

import java.util.List;

import rx.Observable;

public interface LoadPostUseCase {

    Observable<List<Post>> loadPost();
    Observable<List<Post>> loadPost(String query);

}
