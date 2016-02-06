package net.numa08.gochisou.presentation.internal.di.components;

import android.content.Context;

import net.numa08.gochisou.presentation.internal.di.PerActivity;
import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule;
import net.numa08.gochisou.presentation.internal.di.modules.EsaServiceModule;
import net.numa08.gochisou.presentation.internal.di.modules.RealmConfigurationModule;
import net.numa08.gochisou.presentation.view.fragment.LoginFragment;
import net.numa08.gochisou.presentation.view.fragment.TeamListFragment;

import javax.inject.Singleton;

import dagger.Component;

@PerActivity
@Component(modules = {ApplicationModule.class, EsaServiceModule.class, RealmConfigurationModule.class})
public interface ApplicationComponent {
    Context context();

    void inject(LoginFragment fragment);

    void inject(TeamListFragment fragment);
}
