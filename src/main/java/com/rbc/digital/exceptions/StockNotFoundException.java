package com.rbc.digital.exceptions;


public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String stock) {

        super(String.format("Stock with scrip %s not found", stock));
    }
}