package com.example.webshop.model.costumerPurchases;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CostumerPurchase {

    String name;
    String address;
    Integer sumOfPurchases;
}
