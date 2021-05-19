package com.example.helloworldnataliasapplication.models;

public interface OnGetDataListener<T> {
    //this is for callbacks
    void onSuccess(T dataResponse);
    void onFailure();
}
