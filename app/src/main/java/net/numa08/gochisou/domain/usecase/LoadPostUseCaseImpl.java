package net.numa08.gochisou.domain.usecase;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.repository.EsaRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class LoadPostUseCaseImpl implements LoadPostUseCase {

    private final EsaRepository esaRepository;

    @Inject
    public LoadPostUseCaseImpl(EsaRepository esaRepository) {
        this.esaRepository = esaRepository;
    }

    @Override
    public Observable<List<Post>> loadPost() {
        return esaRepository.getPosts("");
    }

    @Override
    public Observable<List<Post>> loadPost(String query) {
        return esaRepository.getPosts(query);
    }

}
