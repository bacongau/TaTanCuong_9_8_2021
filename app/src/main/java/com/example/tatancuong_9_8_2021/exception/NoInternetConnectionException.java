package com.example.tatancuong_9_8_2021.exception;

import java.io.IOException;

public class NoInternetConnectionException extends IOException {
    public NoInternetConnectionException() {
        super("No internet connection");
    }
}
