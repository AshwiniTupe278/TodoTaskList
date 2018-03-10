package com.ashwini.todotask.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ashwini.todotask.R;
import com.ashwini.todotask.adapter.InCompleteTaskListAdapter;
import com.ashwini.todotask.helper.SqliteDBHelper;
import com.ashwini.todotask.model.TaskModel;
import com.ashwini.todotask.utils.Constant;

import java.util.ArrayList;

public class FragmentIncompleteTask extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

//    FloatingActionButton addTask;
    RecyclerView rvIncompleteTask;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TaskModel> taskList = new ArrayList<>();
    SqliteDBHelper dbHelper;
    SwipeRefreshLayout swipeRefreshLayout;

    public FragmentIncompleteTask() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_incomplete_task, container, false);
        rvIncompleteTask = (RecyclerView) view.findViewById(R.id.rvIncompleteTask);
        layoutManager = new LinearLayoutManager(getContext());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        adapter = new InCompleteTaskListAdapter(taskList, getContext());
        rvIncompleteTask.setLayoutManager(layoutManager);
        rvIncompleteTask.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.divider));
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                updateCardView();
            }
        });
        return view;
    }

    public void updateCardView() {




        swipeRefreshLayout.setRefreshing(true);
        dbHelper = new SqliteDBHelper(getActivity());

        Cursor result = dbHelper.getTask(Constant.INCOMPLETE_STATUS);
        if (result.getCount() == 0) {
            taskList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), R.string.no_task_msg, Toast.LENGTH_SHORT).show();
        } else {
            taskList.clear();
            adapter.notifyDataSetChanged();
            while (result.moveToNext()) {
                TaskModel tddObj = new TaskModel();
                tddObj.set_ID(result.getInt(0));
                tddObj.setTaskName(result.getString(1));
                tddObj.setTaskStatus(result.getString(2));
                tddObj.setTaskDescription(result.getString(3));
                taskList.add(tddObj);
            }
            adapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        updateCardView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            Log.d("fff","hgghh");
        }
    }
}