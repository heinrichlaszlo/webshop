package com.example.webshop.model.customer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Customer {
    String webShopId;
    String customerId;
    String customerName;
    String customerAddress;
}
