package io.realm

import android.support.v7.widget.RecyclerView

abstract class RealmBaseRecyclerAdapter<T : RealmObject, VH : RecyclerView.ViewHolder>(protected var realmResults: RealmResults<T>?, autoRefresh: Boolean) : RecyclerView.Adapter<VH>() {
    private val listener: RealmChangeListener?

    init {
        this.listener = if (!autoRefresh)
            null
        else
            RealmChangeListener { notifyDataSetChanged() }
        if (listener != null && realmResults != null) {
            realmResults!!.realm.handlerController.addChangeListenerAsWeakReference(listener)
        }
    }

    fun updateRealmResults(queryResults: RealmResults<T>?) {
        if (listener != null) {
            if (realmResults != null) {
                realmResults!!.realm.removeChangeListener(listener)
            }

            if (queryResults != null) {
                queryResults.realm.addChangeListener(listener)
            }
        }

        realmResults = queryResults
        notifyDataSetChanged()
    }
}