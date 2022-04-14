package com.example.dps924a4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookCardAdapter extends RecyclerView.Adapter<BookCardAdapter.TasksViewHolder> {
    interface booksClickListener{
        void bookClicked(Book book);
    }
    booksClickListener listener;
    private final Context mCtx;
    public List<Book> bookList;

    public BookCardAdapter(Context mCtx, List<Book> bookList) {
        this.mCtx = mCtx;
        this.bookList = bookList;
        this.listener = (MainActivity)mCtx;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_books_card, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCardAdapter.TasksViewHolder holder, int position) {
        Book book = bookList.get(position);
        Picasso.get().load(book.getImageURL()).into(holder.bookImageView);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView bookImageView;
        public TasksViewHolder(View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.book_thumbnail);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            listener.bookClicked(bookList.get(getAdapterPosition()));
        }
    }
}
