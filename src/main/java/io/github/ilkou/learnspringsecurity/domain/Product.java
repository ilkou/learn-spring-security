package io.github.ilkou.learnspringsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private Integer id;
    private String name;
    private Float price;
}
