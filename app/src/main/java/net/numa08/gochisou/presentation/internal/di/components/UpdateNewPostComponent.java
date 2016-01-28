package net.numa08.gochisou.presentation.internal.di.components;

import net.numa08.gochisou.presentation.internal.di.PerActivity;
import net.numa08.gochisou.presentation.internal.di.modules.EsaServiceModule;
import net.numa08.gochisou.presentation.internal.di.modules.RealmConfigurationModule;
import net.numa08.gochisou.presentation.internal.di.modules.UpdateNewPostModule;
import net.numa08.gochisou.presentation.service.EsaAccessService;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
    modules = {RealmConfigurationModule.class, EsaServiceModule.class ,UpdateNewPostModule.class})
public interface UpdateNewPostComponent {
    void inject(EsaAccessService service);
}
