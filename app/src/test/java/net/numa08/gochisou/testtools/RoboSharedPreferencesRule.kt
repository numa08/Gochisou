package net.numa08.gochisou.testtools

import android.content.Context
import org.junit.rules.ExternalResource
import org.robolectric.RuntimeEnvironment

class RoboSharedPreferencesRule : ExternalResource() {

    val sharedPreferences = RuntimeEnvironment.application.getSharedPreferences("test", Context.MODE_PRIVATE)

    override fun before() {
        sharedPreferences
                .edit()
                .clear()
                .commit()
    }

    override fun after() {
        sharedPreferences
        .edit()
        .clear()
        .commit()
    }
}