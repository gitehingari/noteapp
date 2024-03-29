package com.example.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private RecyclerView mNotesList;
    private GridLayoutManager gridLayoutManager;

    private DatabaseReference fNotesDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mNotesList =  findViewById(R.id.notes_list);
        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(gridLayoutManager);


        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
        }





        updateUI();
 if (fAuth.getCurrentUser() != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
}   }
    @Override
    public void onStart() {
        super.onStart();



        FirebaseRecyclerAdapter<NoteModel,NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>(


                NoteModel.class,
                R.layout.single_note_layout,
                NoteViewHolder.class,
                fNotesDatabase



        ) {
            @Override
            protected void populateViewHolder(final NoteViewHolder viewHolder, NoteModel model, int position) {
                String noteId = getRef(position).getKey();

                fNotesDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                        viewHolder.setNoteTitle(title);
                        viewHolder.setNoteTime(timestamp);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        };



    }
    private void updateUI(){
        if (fAuth.getCurrentUser() != null){
                Log.i("MainActivity","fAuth != null");

        }
        else{
            Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity","fAuth = null");

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.main_new_note_btn:
                Intent newIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(newIntent);
                break;
        }

        return true;
    }
}
















