package com.example.webshop.model.webshopPurchase;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WebshopPuchase {

    String webshop;
    Integer sumOfCardPayments;
    Integer sumOfBankPayments;
}
