package com.ashwini.todotask.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashwini.todotask.R;
import com.ashwini.todotask.helper.SqliteDBHelper;
import com.ashwini.todotask.utils.Constant;

/**
 * Created by softdotcom-7 on 10/3/18.
 */

public class AddTaskDialogFragment extends DialogFragment implements View.OnClickListener {

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener !=null)
        {
            onDismissListener.onDismiss(dialog);
        }
    }

    private Button btnSave,btnCancel;
    private CheckBox cbStatusConfirmation;
    private EditText etTask,etDescription;
    private TextView tvStatus;
    private SqliteDBHelper dbHelper;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public AddTaskDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dailog,container,false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCancel = view.findViewById(R.id.btnCancelTask);
        btnSave = view.findViewById(R.id.btnSaveTask);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        cbStatusConfirmation =  view.findViewById(R.id.cbTaskStatus);

        tvStatus = view.findViewById(R.id.tvStatus);
        cbStatusConfirmation.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
        etTask = (EditText) view.findViewById(R.id.etTask);
        etDescription = (EditText) view.findViewById(R.id.etTaskDescription);

        dbHelper = new SqliteDBHelper(context);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCancelTask:
                getDialog().dismiss();
                break;
            case R.id.btnSaveTask:

                if (etTask.getText().length() >= 1) {

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(Constant.TASK_NAME, etTask.getText().toString());
                    contentValues.put(Constant.TASK_STATUS, Constant.INCOMPLETE_STATUS);
                    contentValues.put(Constant.TASK_DESCRIPTION, etDescription.getText().toString());

                    Boolean b = dbHelper.insertInto(contentValues);
                    if (b) {
                        Toast.makeText(context, R.string.task_add_success_msg, Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();

                    } else {
                        Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.empty_field_msg, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
