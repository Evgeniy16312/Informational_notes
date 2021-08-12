package com.example.informational_notes;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.informational_notes.databinding.ActivityMainBinding;

import java.util.UUID;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

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

        cardSource = new CardsSourceFirebaseImpl();

        adapter = new ItemAdapter(cardSource);

        cardSource.init(cardSource -> adapter.notifyDataSetChanged());

        recyclerView.setHasFixedSize(true); // если у нас все элементы одинакого размера, тот это действие делает работу recyclerView быстрее.
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider, null));
        recyclerView.addItemDecoration(itemDecoration);

        // меняем Layout у recycleView при повороте экрана (делаем в 2 столбца)
        int SpanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, SpanCount));


        SlideInLeftAnimator animator = new SlideInLeftAnimator(new OvershootInterpolator());
        animator.setAddDuration(150);
        animator.setChangeDuration(150);
        animator.setMoveDuration(150);
        animator.setRemoveDuration(150);
        recyclerView.setItemAnimator(animator);

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
                CardData cardData = new CardData(getResources().getString(R.string.title5),
                        getResources().getString(R.string.description5), R.drawable.brasil_spider, false);
                cardData.setId(UUID.randomUUID().toString());
                cardSource.addCardData(cardData);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // В билдере указываем заголовок окна. Можно указывать как ресурс,
                // так и строку
                builder.setTitle("Удалить картинку")
                        // Указываем сообщение в окне. Также есть вариант со
                        // строковым параметром
                        .setMessage("Вы уверены что хотите удалить ?")
                        // Можно указать и пиктограмму
                        .setIcon(R.mipmap.ic_launcher_round)
                        // Из этого окна нельзя выйти кнопкой Back
                        .setCancelable(false)
                        // Устанавливаем кнопку. Название кнопки также можно
                        // задавать строкой
                        .setPositiveButton("Да",
                                // Ставим слушатель, нажатие будем обрабатывать
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        cardSource.deleteCardData(currentPosition);
                                        adapter.notifyItemRemoved(currentPosition);
                                    }
                                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "отмена действия", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                Toast.makeText(MainActivity.this, "Диалог открыт", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_update:
                CardData cardData = new CardData(getResources().getString(R.string.title6),
                        getResources().getString(R.string.description6), R.drawable.pauk_pticeed, false);
                cardData.setId(cardSource.getCardData(currentPosition).getId());
                cardSource.updateCardData(currentPosition, cardData);
                adapter.notifyItemChanged(currentPosition);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}