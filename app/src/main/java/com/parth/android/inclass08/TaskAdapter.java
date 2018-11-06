package com.parth.android.inclass08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class TaskAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Task> tasks;
    private Context context;
    private TaskOperations taskOperations;

    public TaskAdapter(ArrayList<Task> tasks, Context context, TaskOperations taskOperations){
        this.tasks = tasks;
        this.context = context;
        this.taskOperations = taskOperations;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = (Task) getItem(position);
        if (task!=null){
            final ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.add_task_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.Note = (TextView) convertView.findViewById(R.id.textViewNote);
                viewHolder.Priority = (TextView) convertView.findViewById(R.id.textViewPriority);
                viewHolder.Time = (TextView) convertView.findViewById(R.id.textViewTime);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setStatus(isChecked);
                    taskOperations.onCheckBoxClick(task);
                }
            });

            viewHolder.Note.setText(task.getNote());
            viewHolder.Priority.setText(task.getPriority());
            viewHolder.checkBox.setChecked(task.isStatus());
            String date = messageTime(task);
            if (!(date.equals("") || date.equals(null))) {
                viewHolder.Time.setText(date);
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView Note;
        TextView Priority;
        TextView Time;
        CheckBox checkBox;

    }

    public String messageTime(Task task) {
        PrettyTime prettyTime = new PrettyTime();

        String dateString = task.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return prettyTime.format(convertedDate);

    }

    interface TaskOperations{
        public void onCheckBoxClick(Task param);
    }
}
