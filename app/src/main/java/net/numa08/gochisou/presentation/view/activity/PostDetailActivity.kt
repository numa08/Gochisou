package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.presentation.view.fragment.PostDetailFragment
import org.jetbrains.anko.support.v4.withArguments
import javax.inject.Inject

class PostDetailActivity() : AppCompatActivity() {

    val fullName by lazy { intent!!.getStringExtra("fullName") }

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set

    lateinit var realm: Realm
    lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        realm = Realm.getInstance(realmConfiguration)
        post = realm.where(Post::class.java).equalTo("fullName", fullName).findFirst()
        setContentView(R.layout.activity_post_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = post.name
        supportActionBar?.subtitle = post.category
        val fragment = PostDetailFragment().withArguments("fullName" to fullName)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}