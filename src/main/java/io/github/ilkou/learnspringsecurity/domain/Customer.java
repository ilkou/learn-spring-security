package io.github.ilkou.learnspringsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Customer {

    private Integer id;
    private String fullname;
}
