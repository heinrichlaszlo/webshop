package com.example.webshop.model.payments;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Payment {
    String webShopId;
    String customerId;
    String paymentMethod;
    Integer amount;
    String bankAccountNumber;
    String cardNumber;
    Date paymentDate;
}
