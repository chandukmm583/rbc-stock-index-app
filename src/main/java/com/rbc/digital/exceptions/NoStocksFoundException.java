package com.rbc.digital.exceptions;


public class NoStocksFoundException extends RuntimeException {

    public NoStocksFoundException() {

        super("No data found in application please upload data");
    }
}