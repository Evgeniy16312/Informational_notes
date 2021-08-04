package com.example.informational_notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.informational_notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private CardSource cardSource;
    private RecyclerView recyclerView;
    private int currentPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recyclerView);

        cardSource = new CardSourceImpl(this);

        adapter = new ItemAdapter(cardSource);


        recyclerView.setHasFixedSize(true); // если у нас все элементы одинакого размера, тот это действие делает работу recyclerView быстрее.
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider, null));
        recyclerView.addItemDecoration(itemDecoration);

        // меняем Layout у recycleView при повороте экрана (делаем в 2 столбца)
        int SpanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, SpanCount));

        adapter.setListener((view, position) -> {
            currentPosition = position;
            view.showContextMenu(20, 20);
        });

        registerForContextMenu(recyclerView);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make
                (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cards_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                cardSource.addCardData(new CardData(getResources().getString(R.string.title6),
                        getResources().getString(R.string.description6), R.drawable.pauk_pticeed, false));
                adapter.notifyItemChanged(cardSource.size() - 1);
                recyclerView.scrollToPosition(cardSource.size() - 1);
                return true;
            case R.id.action_clear:
                cardSource.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                cardSource.deleteCardData(currentPosition);
                adapter.notifyItemRemoved(currentPosition);

                return true;
            case R.id.action_update:
                cardSource.updateCardData(currentPosition, new CardData(getResources().getString(R.string.title6),
                        getResources().getString(R.string.description6), R.drawable.pauk_pticeed, false));
                adapter.notifyItemChanged(currentPosition);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}