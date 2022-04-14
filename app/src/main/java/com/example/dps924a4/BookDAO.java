package com.example.dps924a4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insertBook(Book book);

    @Delete
    void deleteBook(Book book);

    @Query("SELECT * FROM Book")
    List<Book> getBooks();

    @Query("SELECT * FROM Book WHERE id = :id")
    Book getBookByID(String id);
}
