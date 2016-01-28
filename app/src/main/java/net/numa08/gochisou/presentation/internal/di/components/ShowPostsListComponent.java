package net.numa08.gochisou.presentation.internal.di.components;

import net.numa08.gochisou.presentation.internal.di.PerActivity;
import net.numa08.gochisou.presentation.internal.di.modules.RealmConfigurationModule;
import net.numa08.gochisou.presentation.internal.di.modules.RealmLoadPostsUseCaseModule;
import net.numa08.gochisou.presentation.view.fragment.PostListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {RealmConfigurationModule.class,RealmLoadPostsUseCaseModule.class})
public interface ShowPostsListComponent {
    void inject(PostListFragment fragment);
}
