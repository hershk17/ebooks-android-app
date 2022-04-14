package com.example.dps924a4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NetworkingService.NetworkingListener, BooksAdapter.booksClickListener {

    ArrayList<Book> books = new ArrayList<>();
    RecyclerView recyclerView;
    BooksAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonService = ((MyApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        recyclerView = findViewById(R.id.search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BooksAdapter(this,books);
        recyclerView.setAdapter(adapter);
        setTitle("eBooks");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);

        MenuItem searchViewMenuItem = menu.findItem(R.id.nav_search);

        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        String searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for books");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("query", query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                    networkingManager.searchBooks(newText);
                }
                else {
                    books = new ArrayList<>(0);
                    adapter.bookList = books;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void dataListener(String jsonString) {
        books =  jsonService.getBooksFromJSON(jsonString);
        adapter = new BooksAdapter(this,books);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void bookClicked(Book book) {
        Intent intent = new Intent(this,BookDetailsActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}