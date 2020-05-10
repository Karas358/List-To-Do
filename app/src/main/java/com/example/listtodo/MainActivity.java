package com.example.listtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    private List<Task> toDoList = new ArrayList<>();
    private static RecyclerView recyclerView;
    private ListAdapter listAdapter;
    BottomSheetBehavior bottomSheetBehavior;
    Button btnAdd;
    EditText txtTask, txtSubtitle;
    static TextView txtProgressText, emptyView;
    static ProgressBar progressBar, progress_Bar;
    static ArrayList<Task> taskArrayList;
    Task task;
    TaskRepository taskRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRepository = new TaskRepository(getApplicationContext());
        progressBar = findViewById(R.id.determinateBar);
        progress_Bar = findViewById(R.id.indeterminateBar);
        txtTask = findViewById(R.id.txtTask);
        txtSubtitle = findViewById(R.id.txtSubTitle);
        txtProgressText = findViewById(R.id.txtProgressText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerToDo);
        emptyView = findViewById(R.id.empty_view);
        listAdapter = new ListAdapter(toDoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        LinearLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
            }
            @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        btnAdd = findViewById(R.id.btnConfirm);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = txtTask.getText().toString();
                String sub_title = txtSubtitle.getText().toString();
                TaskRepository taskRepository = new TaskRepository(getApplicationContext());
                task = new Task();
                task.Title = title;
                task.SubTitle = sub_title;
                task.Completed = false;
                taskRepository.InsertTask(task);
                txtTask.setText("");
                txtSubtitle.setText("");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                new getTasks(getApplicationContext()).execute();
            }
        });

        ItemTouchHelper.SimpleCallback itemtouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                task = new Task();
                task.Title = taskArrayList.get(viewHolder.getAdapterPosition()).Title;
                task.SubTitle = taskArrayList.get(viewHolder.getAdapterPosition()).SubTitle;
                task.Completed = taskArrayList.get(viewHolder.getAdapterPosition()).Completed;
                final int pos = viewHolder.getAdapterPosition();

                taskRepository.DeleteTask(taskArrayList.get(viewHolder.getAdapterPosition()));
                new getTasks(getApplicationContext()).execute();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                        .addActionIcon(R.drawable.ic_delete_forever_black_24dp)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemtouchCallback).attachToRecyclerView(recyclerView);
        new getTasks(getApplicationContext()).execute();
    }

    public static class getTasks extends AsyncTask<Void, Void, Void> {

        int countCompleted = 0;
        TaskRepository taskRepository;
        List<Task> taskList;
        Context context;

        public getTasks(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress_Bar.setVisibility(View.VISIBLE);
            taskRepository = new TaskRepository(context);
        }

        @Override
        protected Void doInBackground(Void... voids){
            taskList = taskRepository.getAllTasks();
            taskArrayList = new ArrayList<>();
            taskArrayList.addAll(taskList);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            double len = taskList.size();
            double percentage;
            for(int x = 0; x < len; x++){
                if(taskList.get(x).Completed){
                    countCompleted++;
                }
            }
            percentage = ((countCompleted / len) * 100);
            txtProgressText.setText((int)percentage + "% Done");
            progressBar.setProgress((int)percentage);
            ListAdapter listAdapter = new ListAdapter(taskArrayList);
            recyclerView.setAdapter(listAdapter);

            progress_Bar.setVisibility(View.INVISIBLE);
            if (taskList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.removeAll:
                progress_Bar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                emptyView.setVisibility(View.INVISIBLE);
                taskRepository.deleteAllTasks();
                new getTasks(getApplicationContext()).execute();
                return true;
            default:super.onOptionsItemSelected(menuItem);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
