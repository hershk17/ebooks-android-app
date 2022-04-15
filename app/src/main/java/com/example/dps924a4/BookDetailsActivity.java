package com.example.dps924a4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {
    BookDBService dbService;
    FloatingActionButton fab;
    Book activeBook = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);

        activeBook = getIntent().getParcelableExtra("book");
        ((MyApp)getApplication()).viewingSavedBook = getIntent().getExtras().getBoolean("isSaved");

        setTitle("Details");

        if(!activeBook.getTitle().equals("")) {
            findViewById(R.id.book_title).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_title)).setText(activeBook.getTitle());
        }
        if(activeBook.getAuthors().length > 0) {
            findViewById(R.id.book_authors).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_authors)).setText(String.format("by: %s", String.join(", ", activeBook.getAuthors())));
        }
        if(!activeBook.getPublisher().equals("")) {
            findViewById(R.id.section_publication).setVisibility(View.VISIBLE);
            findViewById(R.id.book_publisher).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_publisher)).setText(String.format("Publisher: %s", activeBook.getPublisher()));
        }
        if(!activeBook.getPublishedDate().equals("")) {
            findViewById(R.id.section_publication).setVisibility(View.VISIBLE);
            findViewById(R.id.book_publishedDate).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_publishedDate)).setText(String.format("Published on: %s", activeBook.getPublishedDate()));
        }
        if(!activeBook.getDescription().equals("")) {
            findViewById(R.id.section_about).setVisibility(View.VISIBLE);
            findViewById(R.id.book_description).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_description)).setText(activeBook.getDescription());
        }
        if(activeBook.getPageCount() != -1) {
            findViewById(R.id.section_other).setVisibility(View.VISIBLE);
            findViewById(R.id.book_pageCount).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_pageCount)).setText(String.format("Pages: %s", String.valueOf(activeBook.getPageCount())));
        }
        if(activeBook.getCategories().length > 0) {
            findViewById(R.id.section_about).setVisibility(View.VISIBLE);
            findViewById(R.id.book_categories).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_categories)).setText(String.format("Categories: %s", String.join(",", activeBook.getCategories())));
        }
        if(activeBook.getAverageRating() != -1) {
            findViewById(R.id.book_averageRating).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_averageRating)).setText(String.format("Score: %s/5", String.valueOf(activeBook.getAverageRating())));
        }
        if(!activeBook.getMaturityRating().equals("")) {
            findViewById(R.id.section_other).setVisibility(View.VISIBLE);
            findViewById(R.id.book_maturityRating).setVisibility(View.VISIBLE);
            String maturityRating = "Yes";
            if(activeBook.getMaturityRating().equals("NOT_MATURE")) {
                maturityRating = "No";
            }
            ((TextView)findViewById(R.id.book_maturityRating)).setText(String.format("Mature: %s", maturityRating));
        }
        if(!activeBook.getImageURL().equals("")) {
            findViewById(R.id.book_thumbnail).setVisibility(View.VISIBLE);
            Picasso.get().load(activeBook.getImageURL()).into(((ImageView)findViewById(R.id.book_thumbnail)));
        }
        if(!activeBook.getLanguage().equals("")) {
            findViewById(R.id.section_other).setVisibility(View.VISIBLE);
            findViewById(R.id.book_language).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.book_language)).setText(String.format("Language: %s", activeBook.getLanguage()));
        }

        Handler handler = new Handler();
        Toast toast = Toast.makeText(this,"Saved to Library!", Toast.LENGTH_SHORT);
        fab = findViewById(R.id.fab);
        if(((MyApp)getApplication()).viewingSavedBook) {
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add));
        }
        fab.setOnClickListener(view -> {
            if(!((MyApp)getApplication()).viewingSavedBook) {
                dbService.insertBook(activeBook);
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check));
                toast.setText("Saved to Library!");
            } else {
                dbService.deleteBook(activeBook);
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add));
                toast.setText("Removed from Library!");
            }
            ((MyApp)getApplication()).viewingSavedBook = !((MyApp)getApplication()).viewingSavedBook;
            toast.show();
            handler.postDelayed(toast::cancel, 1000);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}