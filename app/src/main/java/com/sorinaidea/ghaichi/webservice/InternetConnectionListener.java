package com.sorinaidea.ghaichi.webservice;

public interface InternetConnectionListener {
    void onInternetUnavailable();

    void onCacheUnavailable();
}
