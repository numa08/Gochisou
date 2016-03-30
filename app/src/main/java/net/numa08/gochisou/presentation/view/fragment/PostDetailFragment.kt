package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.fragment_post_detail.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Post
import org.parceler.Parcels
import javax.inject.Inject

open class PostDetailFragment() : Fragment() {

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit var realm: Realm
    lateinit var post: Post

    val fullName by lazy { arguments!!.getString("fullName") }
    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(arguments!!.getParcelable("loginProfile")) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        realm = Realm.getInstance(realmConfiguration)
        post = realm.where(Post::class.java).equalTo("fullName", fullName).findFirst()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_post_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadData(post.bodyHTML, "text/html; charset=utf-8", "UTF-8")
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}