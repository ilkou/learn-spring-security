package io.github.ilkou.learnspringsecurity.web.rest;

import io.github.ilkou.learnspringsecurity.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private static final List<Customer> CUSTOMERS = Arrays.asList(
            new Customer(1, "Ilkou Oussama"),
            new Customer(2, "Designer Achraf")
    );

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable("id") Integer id) {
        return CUSTOMERS.stream().filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("customer " + id + " does not exist"));
    }
}
