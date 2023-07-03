package com.fisco.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private Integer pet_id;
    private String pet_name;
    private String sell_username;
    private String picture_path;
    private String description;
    private double price;
    private String pet_class;
    private boolean has_sold_out;
}
