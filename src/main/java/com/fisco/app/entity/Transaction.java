package com.fisco.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class Transaction {
    private Integer pet_id;
    private String purchase_username;
    private String owner;
    private String transaction_date;
    private Integer price;
}
