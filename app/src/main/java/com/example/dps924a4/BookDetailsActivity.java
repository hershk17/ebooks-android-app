package com.example.dps924a4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {
    BookDBService dbService;
    FloatingActionButton fab;
    Book activeBook = null;
    boolean isSaved = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);

        activeBook = getIntent().getParcelableExtra("book");
        isSaved = getIntent().getExtras().getBoolean("isSaved");

        setTitle(activeBook.getTitle());
        ((TextView)findViewById(R.id.book_title)).setText(activeBook.getTitle());
        ((TextView)findViewById(R.id.book_authors)).setText(String.join(",", activeBook.getAuthors()));
        ((TextView)findViewById(R.id.book_publisher)).setText(activeBook.getPublisher());
        ((TextView)findViewById(R.id.book_publishedDate)).setText(activeBook.getPublishedDate());
        ((TextView)findViewById(R.id.book_description)).setText(activeBook.getDescription());
        ((TextView)findViewById(R.id.book_pageCount)).setText(String.valueOf(activeBook.getPageCount()));
        ((TextView)findViewById(R.id.book_categories)).setText(String.join(",", activeBook.getCategories()));
        ((TextView)findViewById(R.id.book_averageRating)).setText(String.valueOf(activeBook.getAverageRating()));
        ((TextView)findViewById(R.id.book_ratingCount)).setText(String.valueOf(activeBook.getRatingCount()));
        ((TextView)findViewById(R.id.book_maturityRating)).setText(activeBook.getMaturityRating());
        Picasso.get().load(activeBook.getImageURL()).into(((ImageView)findViewById(R.id.book_thumbnail)));
        ((TextView)findViewById(R.id.book_language)).setText(activeBook.getLanguage());
        ((TextView)findViewById(R.id.book_link)).setText(activeBook.getLink());

        fab = findViewById(R.id.fab);
        if(isSaved) {
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add));
        }
        Handler handler = new Handler();
        Toast toast = Toast.makeText(this,"Saved to Library!", Toast.LENGTH_SHORT);
        fab.setOnClickListener(view -> {
            if(!isSaved) {
                dbService.insertBook(activeBook);
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check));
                toast.setText("Saved to Library!");
            } else {
                dbService.deleteBook(activeBook);
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add));
                toast.setText("Removed from Library!");
            }
            isSaved = !isSaved;
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