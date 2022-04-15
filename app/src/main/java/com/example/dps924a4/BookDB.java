package com.example.dps924a4;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(version = 2,entities = {Book.class})
@TypeConverters({Converters.class})
abstract public class BookDB extends RoomDatabase {
    public abstract BookDAO getDao();
}