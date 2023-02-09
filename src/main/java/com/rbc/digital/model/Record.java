package com.rbc.digital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;
    private Integer quarter;
    private String stock;
    private String date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private Double percent_change_price;
    private Double percent_change_volume_over_last_wk;
    private Long previous_weeks_volume;
    private Double next_weeks_open;
    private Double next_weeks_close;
    private Double percent_change_next_weeks_price;
    private Integer days_to_next_dividend;
    private Double percent_return_next_dividend;

}
