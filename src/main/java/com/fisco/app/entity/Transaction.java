package com.fisco.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Transaction {
    private Integer pet_id;
    private String purchase_username;
    private String sell_username;
    private String transaction_date;
    private Integer price;
}
