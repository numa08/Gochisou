package net.numa08.gochisou.presentation.presenter;

import android.support.annotation.NonNull;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.presentation.PostListView;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class TweetListPresenter implements Presenter{

    private final RealmConfiguration realmConfiguration;
    private PostListView postListView;
    private Realm realm;

    @Inject
    public TweetListPresenter(RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
        realm = Realm.getInstance(realmConfiguration);
    }

    public void setView(@NonNull PostListView view) {
        this.postListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
        realm.close();
    }

    public RealmQuery<Post> loadPost() {
        return realm.where(Post.class);
    }
}
