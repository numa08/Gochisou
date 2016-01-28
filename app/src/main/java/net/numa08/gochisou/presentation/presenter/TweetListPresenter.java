package net.numa08.gochisou.presentation.presenter;

import android.support.annotation.NonNull;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.usecase.LoadPostUseCase;
import net.numa08.gochisou.presentation.PostListView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class TweetListPresenter implements Presenter{

    private final LoadPostUseCase useCase;
    private PostListView postListView;

    @Inject
    public TweetListPresenter(LoadPostUseCase useCase) {
        this.useCase = useCase;
    }

    public void setView(@NonNull PostListView view) {
        this.postListView = view;
    }

    @Override
    public void resume() {
        useCase.loadPost()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<Post> posts) {
                        if (postListView != null) {
                            postListView.renderPostList(posts);
                        }
                    }
                });
    }

    @Override
    public void pause() {

    }
}
