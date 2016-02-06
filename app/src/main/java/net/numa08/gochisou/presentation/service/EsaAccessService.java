package net.numa08.gochisou.presentation.service;

import android.util.Log;

import net.numa08.gochisou.data.service.EsaService;
import net.numa08.gochisou.data.model.PageNation;
import net.numa08.gochisou.data.model.Team;

import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.androidannotations.api.support.app.AbstractIntentService;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

@EIntentService
public class EsaAccessService extends AbstractIntentService {

    @Inject
    RealmConfiguration realmConfiguration;
    @Inject
    EsaService esaService;

    public EsaAccessService() {
        super(EsaAccessService.class.getName());
    }

    @ServiceAction
    void loadTeam(String token) {
        final Observable<PageNation.TeamPageNation> observable = esaService
                .teams(token)
                .subscribeOn(Schedulers.newThread());
        observable
                .map(PageNation::getList)
                .subscribe(new Observer<List<Team>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e("gochisou", "get team error cause ", e);
                    }

                    @Override
                    public void onNext(List<Team> teams) {
                        final Realm realm = Realm.getInstance(realmConfiguration);
                        realm.executeTransaction(r -> r.copyToRealmOrUpdate(teams));
                        realm.close();
                    }
                });
        observable
                .map(PageNation::getNextPage)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }

}
