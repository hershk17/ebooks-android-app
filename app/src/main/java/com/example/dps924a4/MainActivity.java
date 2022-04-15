package com.example.dps924a4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NetworkingService.NetworkingListener, BookCardAdapter.booksClickListener, BookDBService.DBCallBackInterface {

    NetworkingService networkingManager;
    JsonService jsonService;

    BookCardAdapter fictionBooksAdapter;
    BookCardAdapter mysteryBooksAdapter;
    BookCardAdapter romanceBooksAdapter;
    BookCardAdapter fantasyBooksAdapter;

    RecyclerView recyclerViewMysteryBooks;
    RecyclerView recyclerViewRomanceBooks;
    RecyclerView recyclerViewFantasyBooks;
    RecyclerView recyclerViewFictionBooks;

    BookDBService dbService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonService = ((MyApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        setTitle("eBooks App");

        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);
        dbService.listener = this;

        fictionBooksAdapter = new BookCardAdapter(this, new ArrayList<>(0));
        mysteryBooksAdapter = new BookCardAdapter(this, new ArrayList<>(0));
        romanceBooksAdapter = new BookCardAdapter(this, new ArrayList<>(0));
        fantasyBooksAdapter = new BookCardAdapter(this, new ArrayList<>(0));

        recyclerViewFictionBooks = findViewById(R.id.fiction_books);
        recyclerViewMysteryBooks = findViewById(R.id.mystery_books);
        recyclerViewRomanceBooks = findViewById(R.id.romance_books);
        recyclerViewFantasyBooks = findViewById(R.id.fantasy_books);

        recyclerViewFictionBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMysteryBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRomanceBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFantasyBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewFictionBooks.setAdapter(fictionBooksAdapter);
        recyclerViewMysteryBooks.setAdapter(mysteryBooksAdapter);
        recyclerViewRomanceBooks.setAdapter(romanceBooksAdapter);
        recyclerViewFantasyBooks.setAdapter(fantasyBooksAdapter);

        networkingManager.getFictionBooks();
        networkingManager.getMysteryBooks();
        networkingManager.getRomanceBooks();
        networkingManager.getFantasyBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbService.listener = this;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent;
        switch(item.getItemId()) {
            case R.id.nav_search:
                intent = new Intent(this,SearchBooksActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_saved:
                intent = new Intent(this,SavedBooksActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void dataListener(String jsonString, String type) {
        ArrayList<Book> books = jsonService.getBooksFromJSON(jsonString);
        switch(type) {
            case "fiction":
                fictionBooksAdapter.bookList = books;
                recyclerViewFictionBooks.setAdapter(fictionBooksAdapter);
                break;
            case "fantasy":
                fantasyBooksAdapter.bookList = books;
                recyclerViewFantasyBooks.setAdapter(fantasyBooksAdapter);
                break;
            case "romance":
                romanceBooksAdapter.bookList = books;
                recyclerViewRomanceBooks.setAdapter(romanceBooksAdapter);
                break;
            case "mystery":
                mysteryBooksAdapter.bookList = books;
                recyclerViewMysteryBooks.setAdapter(mysteryBooksAdapter);
                break;
        }
    }

    @Override
    public void bookClicked(Book book) {
        intent = new Intent(this,BookDetailsActivity.class);
        intent.putExtra("book", book);
        dbService.getBookByID(book.getId());
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