package net.numa08.gochisou.data.repository;

import net.numa08.gochisou.data.service.EsaService;
import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.repository.EsaRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class RetrofitEsaRepository implements EsaRepository {

    final EsaService esaService;

    @Inject
    public RetrofitEsaRepository(EsaService esaService) {
        this.esaService = esaService;
    }

    @Override
    public Observable<List<Post>> getPosts(String query) {
        return esaService.posts(query);
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return esaService.posts("");
    }

    @Override
    public Observable<Post> savePost(Post post) {
        return esaService.post(post);
    }

    @Override
    public Observable<List<Post>> savePost(List<Post> posts) {
        throw new UnsupportedOperationException("Esa-API doesn't support multi posts object.");
    }
}
