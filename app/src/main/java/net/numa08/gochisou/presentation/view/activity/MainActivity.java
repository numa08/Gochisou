package net.numa08.gochisou.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.numa08.gochisou.R;
import net.numa08.gochisou.presentation.view.fragment.LoginFragment_;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, LoginFragment_.builder().build())
                .commit();
    }
}
