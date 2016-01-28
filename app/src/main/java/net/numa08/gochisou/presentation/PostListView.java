package net.numa08.gochisou.presentation;

import net.numa08.gochisou.domain.model.Post;

import java.util.Collection;

public interface PostListView {

    void renderPostList(Collection<Post> posts);

    void showPost(Post post);

}
