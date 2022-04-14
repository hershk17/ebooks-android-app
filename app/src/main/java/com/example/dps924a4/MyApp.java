package com.example.dps924a4;

import android.app.Application;

public class MyApp extends Application {
    private final NetworkingService networkingService = new NetworkingService();
    private final JsonService jsonService = new JsonService();

    public NetworkingService getNetworkingService() {
        return networkingService;
    }
    public JsonService getJsonService() {
        return jsonService;
    }
}
