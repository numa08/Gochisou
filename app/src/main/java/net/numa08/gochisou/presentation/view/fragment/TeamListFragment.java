package net.numa08.gochisou.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;

import net.numa08.gochisou.GochisouApplication;
import net.numa08.gochisou.data.model.Team;
import net.numa08.gochisou.presentation.view.adapter.TeamListAdapter;

import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

@EFragment
public class TeamListFragment extends ListFragment {

    @Inject
    RealmConfiguration realmConfiguration;

    Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GochisouApplication.application().getApplicationComponent().inject(this);
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RealmResults<Team> teams = realm.allObjects(Team.class);
        final TeamListAdapter adapter = new TeamListAdapter(view.getContext(), teams);
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
