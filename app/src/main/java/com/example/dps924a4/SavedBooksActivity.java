package com.example.dps924a4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SavedBooksActivity extends AppCompatActivity
        implements SavedBookAdapter.booksClickListener, BookDBService.DBCallBackInterface {

    RecyclerView recyclerView;
    SavedBookAdapter adapter;
    BookDBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_books);
        setTitle("Saved Books");

        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);
        dbService.listener = this;

        recyclerView = findViewById(R.id.saved_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedBookAdapter(this, new ArrayList<>(0));
        recyclerView.setAdapter(adapter);

        dbService.getBooks();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void bookInserted() {
        dbService.getBooks();
    }

    @Override
    public void bookDeleted() {
        dbService.getBooks();
    }

    @Override
    public void listOfBooksFromDB(List<Book> books) {
        adapter.bookList = books;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bookByIdFromDB(Book book) {}

    @Override
    public void bookClicked(Book book) {
        Intent intent = new Intent(this,BookDetailsActivity.class);
        intent.putExtra("book", book);
        intent.putExtra("isSaved", true);
        startActivity(intent);
    }
}