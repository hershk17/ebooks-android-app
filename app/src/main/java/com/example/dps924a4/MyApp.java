package com.example.dps924a4;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private final NetworkingService networkingService = new NetworkingService();
    private final JsonService jsonService = new JsonService();

    BookDBService dbService = new BookDBService();
    boolean viewingSavedBook = false;

    public NetworkingService getNetworkingService() {
        return networkingService;
    }
    public JsonService getJsonService() {
        return jsonService;
    }
}
