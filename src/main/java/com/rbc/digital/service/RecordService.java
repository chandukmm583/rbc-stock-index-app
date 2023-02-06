package com.rbc.digital.service;

import com.rbc.digital.dao.RecordRepository;
import com.rbc.digital.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public List<Record> getByStock(String stock) {
        return recordRepository.findByStock(stock);
    }
}
