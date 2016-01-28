package net.numa08.gochisou.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;

import net.numa08.gochisou.domain.model.Post;
import net.numa08.gochisou.domain.usecase.LoadPostUseCase;
import net.numa08.gochisou.presentation.PostListView;
import net.numa08.gochisou.presentation.presenter.TweetListPresenter;
import net.numa08.gochisou.presentation.service.EsaAccessService;
import net.numa08.gochisou.presentation.service.EsaAccessService_;

import org.androidannotations.annotations.EFragment;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

@EFragment
public class PostListFragment extends ListFragment implements PostListView {

    @Inject
    TweetListPresenter tweetListPresenter;

    RealmBaseAdapter<Post> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject
        EsaAccessService_.intent(getContext()).loadPost();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tweetListPresenter.setView(this);
        EsaAccessService_.intent(getContext()).loadPost();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        tweetListPresenter.resume();
    }

    @Override
    public void onPause() {
        tweetListPresenter.pause();
        super.onPause();
    }

    @Override
    public void renderPostList(Collection<Post> posts) {
        if (getListAdapter() != null) {
            return;
        }
        if (posts instanceof RealmResults) {
            adapter = new RealmBaseAdapter<Post>(getContext(), (RealmResults<Post>) posts, true) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return null;
                }
            };
            setListAdapter(adapter);
        }
    }

    @Override
    public void showPost(Post post) {}
}
