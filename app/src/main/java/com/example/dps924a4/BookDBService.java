package com.example.dps924a4;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookDBService {
    interface DBCallBackInterface{
        void bookInserted();
        void listOfBooksFromDB(List<Book> books);
        void bookDeleted();
        void bookByIdFromDB(Book book);
    }

    BookDB db;
    DBCallBackInterface listener;
    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

    public void getInstance(Context context){
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), BookDB.class, "owners_cars_db").build();
        }
    }

    public void insertBook(Book book){
        dbExecutor.execute(() -> {
            db.getDao().insertBook(book);
            dbHandler.post(() -> listener.bookInserted());
        });
    }

    public void getBooks(){
        dbExecutor.execute(() -> {
            List<Book> books = db.getDao().getBooks();
            dbHandler.post(() -> listener.listOfBooksFromDB(books));
        });
    }

    public void deleteBook(Book book){
        dbExecutor.execute(() -> {
            db.getDao().deleteBook(book);
            dbHandler.post(() -> listener.bookDeleted());
        });
    }

    public void getBookByID(String id){
        dbExecutor.execute(() -> {
            Book book = db.getDao().getBookByID(id);
            dbHandler.post(() -> listener.bookByIdFromDB(book));
        });
    }
}
