package net.numa08.gochisou.domain.usecase;

import net.numa08.gochisou.domain.model.Post;

import java.util.List;

import rx.Observable;

public interface SavePostUseCase {

    Observable<Post> savePost(Post post);

    Observable<List<Post>> savePost(List<Post> posts);

}
