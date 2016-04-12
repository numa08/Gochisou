package net.numa08.gochisou.testtools

import net.numa08.gochisou.BuildConfig
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import org.robolectric.manifest.AndroidManifest
import org.robolectric.res.Fs

class MyTestRunner(k: Class<*>) : RobolectricGradleTestRunner(k) {

    override fun getAppManifest(config: Config?): AndroidManifest?
            = AndroidManifest(
            Fs.fileFromPath("src/main/AndroidManifest.xml"),
            Fs.fileFromPath("build/intermediates/res/merged/${BuildConfig.FLAVOR}/debug"),
            Fs.fileFromPath("build/intermediates/assets/${BuildConfig.FLAVOR}/debug")
    )

}