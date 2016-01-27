package net.numa08.gochisou.presentation.service;

import android.support.annotation.NonNull;
import android.util.Log;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.usecase.LoadPostUseCase;
import net.numa08.gochisou.domain.usecase.SavePostUseCase;

import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.androidannotations.api.support.app.AbstractIntentService;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

@EIntentService
public class EsaAccessService extends AbstractIntentService {

    @Inject
    LoadPostUseCase loadEsaUseCase;
    @Inject
    SavePostUseCase savePostUseCase;

    public EsaAccessService() {
        super("EsaAccessService");
        //inject
    }

    @ServiceAction
    void loadPostWithQuery(String query) {
        loadEsaUseCase
                .loadPost(query)
                .subscribe(loadPostObserver());
    }

    @NonNull
    private Observer<List<Post>> loadPostObserver() {
        return new Observer<List<Post>>() {
            @Override
            public void onCompleted() {
                Log.d("gochisou", "load post on completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("gochisou", "load post on error", e);
            }

            @Override
            public void onNext(List<Post> posts) {
                savePostUseCase.savePost(posts);
            }
        };
    }

    @ServiceAction
    void loadPost(){
        loadEsaUseCase
                .loadPost()
                .subscribe(loadPostObserver());
    }
}
