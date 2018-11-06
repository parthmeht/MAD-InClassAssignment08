package com.parth.android.inclass08;

public class Task {
    private String note,priority,time;
    private boolean checkbox;

    public Task(String note, String priority, String time, boolean checkbox) {
        this.note = note;
        this.priority = priority;
        this.time = time;
        this.checkbox = checkbox;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    @Override
    public String toString() {
        return "Task{" +
                "note='" + note + '\'' +
                ", priority='" + priority + '\'' +
                ", time='" + time + '\'' +
                ", checkbox=" + checkbox +
                '}';
    }
}
