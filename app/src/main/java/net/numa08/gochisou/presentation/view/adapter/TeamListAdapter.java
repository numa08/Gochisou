package net.numa08.gochisou.presentation.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.numa08.gochisou.data.model.Team;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class TeamListAdapter extends RealmBaseAdapter<Team> {

    public TeamListAdapter(Context context, RealmResults<Team> realmResults) {
        super(context, realmResults, true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView textView = new TextView(parent.getContext());
        textView.setText(realmResults.get(position).getName());
        return textView;
    }
}
