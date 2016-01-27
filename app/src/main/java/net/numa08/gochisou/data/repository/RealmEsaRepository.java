package net.numa08.gochisou.data.repository;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.repository.EsaRepository;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

public class RealmEsaRepository implements EsaRepository {

    final Realm realm;
    @Inject
    public RealmEsaRepository(RealmConfiguration configuration) {
        realm = Realm.getInstance(configuration);
    }

    @Override
    public Observable<List<Post>> getPosts(String query) {
        return realm.where(Post.class).findAllAsync()
                .asObservable()
                .map((Func1<RealmResults<Post>, List<Post>>) posts -> posts);
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return realm.where(Post.class).findAllAsync()
                .asObservable()
                .map((Func1<RealmResults<Post>, List<Post>>) posts -> posts);
    }

    @Override
    public Observable<Post> savePost(Post post) {
        realm.beginTransaction();
        final Post saved = realm.copyToRealmOrUpdate(post);
        realm.commitTransaction();
        return Observable.just(saved);
    }

    @Override
    public Observable<List<Post>> savePost(List<Post> posts) {
        realm.beginTransaction();
        final List<Post> saved = realm.copyToRealmOrUpdate(posts);
        realm.commitTransaction();
        return Observable.just(saved);
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            realm.close();
        }
    }
}
