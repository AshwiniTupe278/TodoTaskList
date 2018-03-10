package com.ashwini.todotask.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ashwini.todotask.adapter.ViewPagerAdapter;
import com.ashwini.todotask.R;
import com.ashwini.todotask.fragments.AddTaskDialogFragment;
import com.ashwini.todotask.fragments.FragmentIncompleteTask;
import com.ashwini.todotask.fragments.FragmentCompleteTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {

    private ViewPager vpTaskStatus;
    private ViewPagerAdapter vpAdapter;
    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private Dialog addTaskDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        vpTaskStatus = (ViewPager) findViewById(R.id.vpTaskStatus);
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpAdapter.addFragment(new FragmentIncompleteTask(), getString(R.string.incomplete_task));
        vpAdapter.addFragment(new FragmentCompleteTask(), getString(R.string.complate_task));
        vpTaskStatus.setAdapter(vpAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vpTaskStatus);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
                FragmentManager manager = getFragmentManager();

                AddTaskDialogFragment dialogFragment = new AddTaskDialogFragment();
                dialogFragment.show(manager,"addTask");

                dialogFragment.setOnDismissListener(this);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("pause","pause");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d("ggg","hhh");
       int pos = vpTaskStatus.getCurrentItem();

       Fragment activeFragment = vpAdapter.getItem(pos);
       if (pos == 0)
           ((FragmentIncompleteTask) activeFragment).onRefresh();
    }
}
