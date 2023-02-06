package com.rbc.digital.controller;

import com.rbc.digital.model.Record;
import com.rbc.digital.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockIndexController {
    @Autowired
    private RecordService recordService;
    @GetMapping("records")
    public ResponseEntity<List<Record>> getAll() {
        ArrayList<Record> ret = new ArrayList<>();
        ret.add(new Record());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("records/{stock}")
    public ResponseEntity<List<Record>> getById(@PathVariable String  stock) {

        List<Record> records = recordService.getByStock(stock);
        if (records.size()>0) {
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
