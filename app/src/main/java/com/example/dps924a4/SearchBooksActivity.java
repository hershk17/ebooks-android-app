package com.example.dps924a4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchBooksActivity extends AppCompatActivity
        implements NetworkingService.NetworkingListener, BookAdapter.booksClickListener, BookDBService.DBCallBackInterface {

    RecyclerView recyclerView;
    BookAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;
    BookDBService dbService;
    Intent intent;
    ArrayList<Book> searchResults = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);
        setTitle("Search Books");

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonService = ((MyApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);
        dbService.listener = this;

        recyclerView = findViewById(R.id.search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this, new ArrayList<>(0));
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            searchResults = savedInstanceState.getParcelableArrayList("results");
            adapter.bookList = searchResults;
            recyclerView.setAdapter(adapter);
        }

        SearchView searchView = findViewById(R.id.search_bar);
        String searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for books");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                networkingManager.searchBooks(query);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("results", searchResults);
    }

    @Override
    public void dataListener(String jsonString, String type) {
        searchResults = jsonService.getBooksFromJSON(jsonString);
        adapter.bookList = searchResults;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bookClicked(Book book) {
        intent = new Intent(this,BookDetailsActivity.class);
        intent.putExtra("book", book);
        dbService.getBookByID(book.getId());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void bookInserted() {}

    @Override
    public void listOfBooksFromDB(List<Book> books) {}

    @Override
    public void bookDeleted() {}

    @Override
    public void bookByIdFromDB(Book book) {
        if(book != null) {
            intent.putExtra("isSaved", true);
        } else {
            intent.putExtra("isSaved", false);
        }
        startActivity(intent);
    }
}