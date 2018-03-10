package com.ashwini.todotask.adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashwini.todotask.R;
import com.ashwini.todotask.helper.SqliteDBHelper;
import com.ashwini.todotask.model.TaskModel;
import com.ashwini.todotask.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by softdotcom-7 on 10/3/18.
 */

public class CompleteTaskListAdapter extends RecyclerView.Adapter<CompleteTaskListAdapter.TaskListViewHolder> {
    List<TaskModel> taskList = new ArrayList<>();
    Context context;

    public CompleteTaskListAdapter(ArrayList<TaskModel> arrayList, Context context) {
        this.taskList = arrayList;
        this.context = context;
    }

    @Override
    public CompleteTaskListAdapter.TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardlayout, parent, false);
        CompleteTaskListAdapter.TaskListViewHolder toDoListViewHolder = new CompleteTaskListAdapter.TaskListViewHolder(view, context);
        return toDoListViewHolder;
    }

    @Override
    public void onBindViewHolder(CompleteTaskListAdapter.TaskListViewHolder holder, final int position) {

        final TaskModel taskModel = taskList.get(position);

        holder.taskName.setText(taskModel.getTaskName());
        holder.taskDesc.setText(taskModel.getTaskDescription());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = taskModel.get_ID();
                SqliteDBHelper dbHelper = new SqliteDBHelper(view.getContext());
                Cursor b = dbHelper.deleteTask(id);
                if (b.getCount() == 0) {
                    Toast.makeText(view.getContext(), R.string.task_delete_msg, Toast.LENGTH_SHORT).show();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            // Code here will run in UI thread
                            taskList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, taskList.size());
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    Toast.makeText(view.getContext(),  R.string.task_delete_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDesc;
        ImageView ivEdit, ivDelete;


        public TaskListViewHolder(View view, final Context context) {
            super(view);
            taskName =  view.findViewById(R.id.tvTaskName);
            taskDesc =  view.findViewById(R.id.tvTaskDescription);

            ivEdit = view.findViewById(R.id.ivEditTask);
            ivDelete =  view.findViewById(R.id.ivDelete);
            ivEdit.setVisibility(View.INVISIBLE);
        }
    }
}
