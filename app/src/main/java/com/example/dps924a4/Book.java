package com.example.dps924a4;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String title;
    private String[] authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private String[] categories;
    private int averageRating;
    private int ratingCount;
    private String maturityRating;
    private String imageURL;
    private String language;
    private String link;

    public Book() {
        id = "";
        title = "";
        authors = new String[0];
        publisher = "";
        publishedDate = "";
        description = "";
        pageCount = -1;
        categories = new String[0];
        averageRating = -1;
        ratingCount = -1;
        maturityRating = "";
        imageURL = "";
        language = "";
        link = "";
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        authors = in.createStringArray();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        categories = in.createStringArray();
        averageRating = in.readInt();
        ratingCount = in.readInt();
        maturityRating = in.readString();
        imageURL = in.readString();
        language = in.readString();
        link = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeStringArray(authors);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(description);
        parcel.writeInt(pageCount);
        parcel.writeStringArray(categories);
        parcel.writeInt(averageRating);
        parcel.writeInt(ratingCount);
        parcel.writeString(maturityRating);
        parcel.writeString(imageURL);
        parcel.writeString(language);
        parcel.writeString(link);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
