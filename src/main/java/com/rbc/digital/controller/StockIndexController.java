package com.rbc.digital.controller;

import com.rbc.digital.exceptions.NoStocksFoundException;
import com.rbc.digital.exceptions.StockNotFoundException;
import com.rbc.digital.model.Record;
import com.rbc.digital.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockIndexController {
    @Autowired
    private RecordService recordService;
    @GetMapping("records")
    public ResponseEntity<List<Record>> getAll() {
        List<Record> allRecords = recordService.getAllRecords();
        if(allRecords.size()==0) throw  new NoStocksFoundException();
        else return new ResponseEntity<>(allRecords,HttpStatus.OK);
    }

    @GetMapping("records/{stock}")
    public ResponseEntity<List<Record>> getById(@PathVariable String  stock) {

        List<Record> records = recordService.getByStock(stock);
        if (records.size()>0) {
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else {
            throw  new StockNotFoundException(stock);
        }
    }
    @PostMapping(value = "records/bulk" , consumes = "multipart/form-data")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("header") boolean header) {

        Exception processedException = recordService.bulkUpload(file,header);
        if(null == processedException)
            return  new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<String>(String.format("{Error_Code: 400, message: could not process file with error message %s}",processedException.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "records")
    public ResponseEntity<String> deleteRecords() throws Exception {
        Exception deleteException = recordService.deleteAllRecords();
        if(null == deleteException)
            return  new ResponseEntity<>(HttpStatus.OK);
        else throw deleteException;
    }

    @PostMapping(value = "records" )
    public ResponseEntity<String> processRecord(@RequestBody Record record) {

        Exception processedException = recordService.processRecord(record);
        if(null == processedException)
            return  new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<String>(String.format("{Error_Code: 400, message: could not process file with error message %s}",processedException.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
