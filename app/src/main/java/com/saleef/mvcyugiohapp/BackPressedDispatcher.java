package com.saleef.mvcyugiohapp;

public interface BackPressedDispatcher {

    void registerListener(BackPressedListener listener);
    void unregisterListener(BackPressedListener listener);
}
