package com.parth.android.inclass08;

public class Task {
    private String id;
    private String note,priority,time;
    private boolean status;

    public Task(String id, String note, String priority, String time, boolean status) {
        this.id = id;
        this.note = note;
        this.priority = priority;
        this.time = time;
        this.status = status;
    }

    public Task(){

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", note='" + note + '\'' +
                ", priority='" + priority + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                '}';
    }
}
