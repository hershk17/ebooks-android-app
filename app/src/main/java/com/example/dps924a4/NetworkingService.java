package com.example.dps924a4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {
    private final String baseURL = "https://www.googleapis.com/books/v1/volumes";
    private final String searchParam = "?q=";
    private final String fictionQuery = "subject:fiction";
    private final String mysteryQuery = "subject:mystery";
    private final String romanceQuery = "subject:romance";
    private final String fantasyQuery = "subject:fantasy";
    private final String maxParam = "&maxResults=";
    private final String filterParam = "&filter=ebooks&printType=books";
    private final String apiKeyParam = "&key=";
    private final String apiKey = "AIzaSyBl1xbzZDxj9iEiHdfZk6grpf6RCKtwd7o";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String jsonString, String type);
    }

    public NetworkingListener listener;

    public void searchBooks(String query){
        String url = baseURL + searchParam + query + apiKeyParam + apiKey + maxParam + "40" + filterParam;
        connect(url, "search");
    }

    public void getFictionBooks() {
        String url = baseURL + searchParam + fictionQuery + apiKeyParam + apiKey + maxParam + "6";
        connect(url, "fiction");
    }

    public void getMysteryBooks() {
        String url = baseURL + searchParam + mysteryQuery + apiKeyParam + apiKey + maxParam + "6";
        connect(url, "mystery");
    }

    public void getRomanceBooks() {
        String url = baseURL + searchParam + romanceQuery + apiKeyParam + apiKey + maxParam + "6";
        connect(url, "romance");
    }

    public void getFantasyBooks() {
        String url = baseURL + searchParam + fantasyQuery + apiKeyParam + apiKey + maxParam + "6";
        connect(url, "fantasy");
    }

    public void connect(String url, String type){
        networkExecutorService.execute(() -> {
            HttpURLConnection httpURLConnection = null;
            try {
                URL urlObject = new URL(url);
                httpURLConnection = (HttpURLConnection)urlObject.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Content-Type","application/json");

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int inputStreamData = 0;

                StringBuilder jsonString = new StringBuilder();
                while ((inputStreamData = reader.read()) != -1){
                    char current = (char)inputStreamData;
                    jsonString.append(current);
                }
                final String finalJsonString = jsonString.toString();
                networkingHandler.post(() -> listener.dataListener(finalJsonString, type));
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                Objects.requireNonNull(httpURLConnection).disconnect();
            }
        });
    }
}
