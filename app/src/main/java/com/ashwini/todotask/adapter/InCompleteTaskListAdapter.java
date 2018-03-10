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


public class InCompleteTaskListAdapter extends RecyclerView.Adapter<InCompleteTaskListAdapter.TaskListViewHolder> {
    List<TaskModel> taskList = new ArrayList<>();
    Context context;

    public InCompleteTaskListAdapter(ArrayList<TaskModel> arrayList, Context context) {
        this.taskList = arrayList;
        this.context = context;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardlayout, parent, false);
        TaskListViewHolder toDoListViewHolder = new TaskListViewHolder(view, context);
        return toDoListViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, final int position) {

        final TaskModel taskModel = taskList.get(position);

        holder.taskName.setText(taskModel.getTaskName());
        holder.taskDesc.setText(taskModel.getTaskDescription());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = taskModel.get_ID();
                SqliteDBHelper mysqlite = new SqliteDBHelper(view.getContext());
                Cursor b = mysqlite.deleteTask(id);
                if (b.getCount() == 0) {
                    Toast.makeText(view.getContext(), R.string.task_delete_msg, Toast.LENGTH_SHORT).show();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                             taskList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, taskList.size());
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    Toast.makeText(view.getContext(), R.string.task_delete_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dailog);
                dialog.show();
                EditText todoText = (EditText) dialog.findViewById(R.id.etTask);
                EditText todoNote = (EditText) dialog.findViewById(R.id.etTaskDescription);
                CheckBox cb = (CheckBox) dialog.findViewById(R.id.cbTaskStatus);


                if (taskModel.getTaskStatus().matches(Constant.COMPLETE_STATUS)) {
                    cb.setChecked(true);
                }

                todoText.setText(taskModel.getTaskName());
                todoNote.setText(taskModel.getTaskDescription());

                Button save = (Button) dialog.findViewById(R.id.btnSaveTask);
                Button cancel = (Button) dialog.findViewById(R.id.btnCancelTask);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText todoText =  dialog.findViewById(R.id.etTask);
                        EditText todoNote = dialog.findViewById(R.id.etTaskDescription);
                        CheckBox cb =  dialog.findViewById(R.id.cbTaskStatus);
                        if (todoText.getText().length() >= 2) {

                            TaskModel updateTd = new TaskModel();
                            updateTd.set_ID(taskModel.get_ID());
                            updateTd.setTaskName(todoText.getText().toString());
                            updateTd.setTaskDescription(todoNote.getText().toString());

                            if (cb.isChecked()) {
                                updateTd.setTaskStatus(Constant.COMPLETE_STATUS);
                            } else {
                                updateTd.setTaskStatus(Constant.INCOMPLETE_STATUS);
                            }

                            SqliteDBHelper dbHelper = new SqliteDBHelper(view.getContext());

                            Cursor b = dbHelper.updateTask(updateTd);
                            taskList.set(position, updateTd);
                            if (b.getCount() == 0) {
                                Toast.makeText(view.getContext(),"Item status is changed.", Toast.LENGTH_SHORT).show();

                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        taskList.remove(position);
                                        notifyItemRangeChanged(position, taskList.size());
                                        notifyDataSetChanged();
                                    }
                                });
                                dialog.hide();
                            } else {

                                dialog.hide();

                            }

                        } else {
                            Toast.makeText(view.getContext(), R.string.task_empty_msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            taskDesc = view.findViewById(R.id.tvTaskDescription);

            ivEdit =  view.findViewById(R.id.ivEditTask);
            ivDelete =  view.findViewById(R.id.ivDelete);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }


}
