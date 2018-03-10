package com.ashwini.todotask.model;

/**
 * Created by deepmetha on 8/28/16.
 */
public class TaskModel {
    int _ID;
    String taskName, taskStatus, taskDescription;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return "TaskModel {id-" + _ID + ", taskDetails-" + taskName + ", status-" + taskStatus + ", notes-" + taskDescription + "}";
    }

}
