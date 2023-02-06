package com.rbc.digital.dao;

import com.rbc.digital.model.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {
    List<Record> findByStock(String stock);

}
