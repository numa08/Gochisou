package net.numa08.gochisou.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import net.numa08.gochisou.GochisouApplication;
import net.numa08.gochisou.R;
import net.numa08.gochisou.data.service.EsaService;
import net.numa08.gochisou.presentation.internal.di.PerActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @ViewById(R.id.token)
    EditText tokenEditText;

    @PerActivity
    @Inject
    EsaService esaService;
    @PerActivity
    @Inject
    RealmConfiguration realmConfiguration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GochisouApplication.application().getApplicationComponent().inject(this);
    }

    @Click(R.id.login)
    void onClickLogin() {
        final String token = tokenEditText.getText().toString();
        esaService
                .teams(token)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> {
                    final Realm realm = Realm.getInstance(realmConfiguration);
                    realm.executeTransaction(r -> r.copyToRealmOrUpdate(p.getList()));
                    realm.close();

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, TeamListFragment_.builder().build())
                            .commit();
                }, e -> {
                    Toast.makeText(getContext(), R.string.message_invalid_token, Toast.LENGTH_SHORT).show();
                    Log.e("gochisou", "could not login case", e);
                });
    }

}
