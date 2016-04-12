package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import net.numa08.gochisou.presentation.service.EsaAccessService

class MembersPostListFragment : Fragment(), ArgLoginProfile {

    companion object {
        val ARG_SCREEN_NAME = "${MembersPostListFragment::class.qualifiedName}.ARG_SCREEN_NAME"
    }

    val screenName by lazy { arguments!!.getString(ARG_SCREEN_NAME) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EsaAccessService.getPosts(context, loginProfile(), "@$screenName").let { context.startService(it) }
    }

}