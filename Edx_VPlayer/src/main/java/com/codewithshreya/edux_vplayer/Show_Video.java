package com.codewithshreya.edux_vplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import android.view.Menu;

public class Show_Video extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    String name;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__video);

        recyclerView = findViewById(R.id.recyclerview_ShowVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Video");
    }


    private void firebaseSearch(String searchtext)
    {
        String query = searchtext.toLowerCase();
        Query firebaseQuery = databaseReference.orderByChild("search").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerOptions<Member> options = new FirebaseRecyclerOptions.Builder<Member>().setQuery(firebaseQuery, Member.class).build();

        FirebaseRecyclerAdapter<Member, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Member model) {
                holder.setExoPlayer(getApplication(), model.getName(),model.getVideourl());
holder.setOnClickListener(new ViewHolder.Clicklistener() {
    @Override

    public void onItemClick(View view, int position) {
        name = getItem(position).getName();
        url = getItem(position).getVideourl();
        Intent in= new Intent(Show_Video.this, Fullscreen.class);
        in.putExtra("nam", name);
        in.putExtra("ur", url);
        startActivity(in);
    }

    @Override
    public void onItemLongClick(View view, int position) {

        name = getItem(position).getName();
    }
});



            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new ViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }






    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Member> options = new FirebaseRecyclerOptions.Builder<Member>().setQuery(databaseReference, Member.class).build();

        FirebaseRecyclerAdapter<Member, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Member model) {

                holder.setExoPlayer(getApplication(), model.getName(), model.getVideourl());
                holder.setOnClickListener(new ViewHolder.Clicklistener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        name = getItem(position).getName();
                        url = getItem(position).getVideourl();
                        Intent in= new Intent(Show_Video.this, Fullscreen.class);
                        in.putExtra("nam", name);
                        in.putExtra("ur", url);
                        startActivity(in);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        name = getItem(position).getName();

                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new ViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


 
    public  boolean onCreateOptionMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_firebase);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                firebaseSearch(newText);
                return false;
            }
        });
        return  super.onCreateOptionsMenu(menu);
    }
}