package com.rbc.digital.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long recordId;
    private int quarter;
    private String stock;
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private double percent_change_price;
    private double percent_change_volume_over_last_wk;
    private long previous_weeks_volume;
    private double next_weeks_open;
    private double next_weeks_close;
    private double percent_change_next_weeks_price;
    private int days_to_next_dividend;
    private double percent_return_next_dividend;

}
