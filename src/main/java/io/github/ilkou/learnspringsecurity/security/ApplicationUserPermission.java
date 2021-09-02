package io.github.ilkou.learnspringsecurity.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationUserPermission {
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write");

    @Getter
    private final String permission;

}
