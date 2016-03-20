package net.numa08.gochisou.data.testtool.rules

import org.junit.rules.ExternalResource
import org.robolectric.RuntimeEnvironment

class RoboSharedPreferencesRule : ExternalResource() {

    val sharedPreferences = RuntimeEnvironment.application.getSharedPreferences("test", android.content.Context.MODE_PRIVATE)

    override fun before() {
        sharedPreferences
        .edit()
        .clear()
        .commit()
    }
}