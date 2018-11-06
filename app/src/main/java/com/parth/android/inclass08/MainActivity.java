package com.parth.android.inclass08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskOperations {

    public static final String TAG = "DEMO";
    private Spinner spinner;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Button addButton;
    private EditText note;
    private ArrayList<Task> taskArrayList;
    private ListView listView;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("todolist");

        String[] priority = {"High", "Medium", "Low"};

        note = findViewById(R.id.editText);
        addButton = findViewById(R.id.buttonAdd);
        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listView);

        // (3) create an adapter from the list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                priority
        );

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // (4) set the adapter on the spinner
        spinner.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note.getText().toString()==null||note.getText().toString().matches("")){
                    Toast.makeText(v.getContext(),"Enter a Note",Toast.LENGTH_LONG).show();
                }else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date convertedDate = new Date();
                    String id = myRef.push().getKey();
                    Task task = new Task(id,note.getText().toString(),spinner.getSelectedItem().toString(),dateFormat.format(convertedDate),false);
                    Log.d(TAG,task.toString());
                    myRef.child(id).setValue(task);
                    note.setText("");
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskArrayList.get(position);
                myRef.child(task.getId()).setValue(null);
                return true;
            }
        });


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                taskArrayList = new ArrayList<>();
                Log.d(TAG, String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Task task = child.getValue(Task.class);
                    taskArrayList.add(task);
                    Log.d(TAG,task.toString());
                }
                Collections.reverse(taskArrayList);
                setListView(taskArrayList);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void setListView(ArrayList<Task> arrayList) {
        adapter = new TaskAdapter(arrayList,this,this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.show_all){
            setListView(taskArrayList);
        }else if (id==R.id.show_completed){
            setListView(getFilteredListCompleted());
        }else if (id==R.id.show_pending){
            setListView(getFilteredListPending());
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Task> getFilteredListCompleted(){
        ArrayList<Task> t1 = new ArrayList<>();
        for (Task t: taskArrayList) {
            if (t.isStatus())
                t1.add(t);
        }
        return t1;
    }

    private ArrayList<Task> getFilteredListPending(){
        ArrayList<Task> t1 = new ArrayList<>();
        for (Task t: taskArrayList) {
            if (!t.isStatus())
                t1.add(t);
        }
        return t1;
    }

    @Override
    public void onCheckBoxClick(Task param) {
        myRef.child(param.getId()).setValue(param);
    }
}
