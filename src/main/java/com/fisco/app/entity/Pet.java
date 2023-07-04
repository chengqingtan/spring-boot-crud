package com.fisco.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private Integer pet_id;
    private String pet_name;
    private String owner;
    private String picture_path;
    private String description;
    private Integer price;
    private String pet_class;
    private Boolean has_sold_out;
}
