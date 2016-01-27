package net.numa08.gochisou.domain.usecase;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.repository.EsaRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class SvaePostUserCaseImpl implements SavePostUseCase {

    private final EsaRepository esaRepository;

    @Inject
    public SvaePostUserCaseImpl(EsaRepository esaRepository) {
        this.esaRepository = esaRepository;
    }

    @Override
    public Observable<Post> savePost(Post post) {
        return esaRepository.savePost(post);
    }

    @Override
    public Observable<List<Post>> savePost(List<Post> posts) {
        return esaRepository.savePost(posts);
    }
}
