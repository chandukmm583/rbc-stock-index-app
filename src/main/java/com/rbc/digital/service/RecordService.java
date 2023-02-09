package com.rbc.digital.service;

import com.rbc.digital.dao.RecordRepository;
import com.rbc.digital.exceptions.StockNotFoundException;
import com.rbc.digital.model.Record;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private String DEFAULT_HEADER_STRING = "quarter,stock,date,open,high,low,close,volume,percent_change_price,percent_change_volume_over_last_wk,previous_weeks_volume,next_weeks_open,next_weeks_close,percent_change_next_weeks_price,days_to_next_dividend,percent_return_next_dividend";
    @Autowired
    private RecordRepository recordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Record> getByStock(String stock) {
        return recordRepository.findByStock(stock);
    }
    public Exception bulkUpload(MultipartFile file,boolean header){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String headerString;
            if(header){
                headerString = br.readLine();
            }
            else{
                headerString = DEFAULT_HEADER_STRING;
            }
            String[] columns = headerString.split(",");
            HashMap<String,Integer> columnMap = new HashMap<>(columns.length);
            for(int i=0;i<columns.length;i++)
                columnMap.put(columns[i],i);
            List<Record> processedRecords = br.lines().map(line -> processBulkFileLine(line, columnMap)).collect(Collectors.toList());
             recordRepository.saveAll(processedRecords);
        }
        catch (Exception e){
            e.printStackTrace();
            return e;
        }
        return null;
    }

    private Record processBulkFileLine(String line, HashMap<String , Integer> columnMap) {
        String [] lineSplit = line.split(",");
        return new Record(null,
                Integer.parseInt(lineSplit[columnMap.get("quarter")]),
                lineSplit[columnMap.get("stock")],
                lineSplit[columnMap.get("date")],
                lineSplit[columnMap.get("open")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("open")].replaceAll("\\$","")),
                lineSplit[columnMap.get("high")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("high")].replaceAll("\\$","")),
                lineSplit[columnMap.get("low")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("low")].replaceAll("\\$","")),
                lineSplit[columnMap.get("close")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("close")].replaceAll("\\$","")),
                lineSplit[columnMap.get("volume")].trim().length()==0?null:Long.parseLong(lineSplit[columnMap.get("volume")]),
                lineSplit[columnMap.get("percent_change_price")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("percent_change_price")]),
                lineSplit[columnMap.get("percent_change_volume_over_last_wk")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("percent_change_volume_over_last_wk")]),
                lineSplit[columnMap.get("previous_weeks_volume")].trim().length()==0?null:Long.parseLong(lineSplit[columnMap.get("previous_weeks_volume")]),
                lineSplit[columnMap.get("next_weeks_open")].trim().length()==0?null:Double.parseDouble( lineSplit[columnMap.get("next_weeks_open")].replaceAll("\\$","")),
                lineSplit[columnMap.get("next_weeks_close")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("next_weeks_close")].replaceAll("\\$","")),
                lineSplit[columnMap.get("percent_change_next_weeks_price")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("percent_change_next_weeks_price")]),
                lineSplit[columnMap.get("days_to_next_dividend")].trim().length()==0?null:Integer.parseInt(lineSplit[columnMap.get("days_to_next_dividend")]),
                lineSplit[columnMap.get("percent_return_next_dividend")].trim().length()==0?null:Double.parseDouble(lineSplit[columnMap.get("percent_return_next_dividend")]));

    }

    public Exception processRecord(Record record) {
        try{
            recordRepository.save(record);
            return null;
        }
        catch (Exception e){
            return e;
        }
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Exception deleteAllRecords() {
        try{
            recordRepository.deleteAll();
            return null;
        }
        catch (Exception e){
            return e;
        }
    }
    public Exception deleteRecord(String stock){
        try {
            List<Record> recordsToDelete = getByStock(stock);
            if(recordsToDelete.size() ==0)
                return new StockNotFoundException(stock);
            recordRepository.deleteAll(recordsToDelete);
            return null;
        }
        catch (Exception e){
            return e;
        }
    }
}
