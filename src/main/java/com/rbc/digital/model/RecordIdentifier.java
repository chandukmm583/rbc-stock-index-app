package com.rbc.digital.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RecordIdentifier implements Serializable {
    private String stock;
    private LocalDate date;

}
