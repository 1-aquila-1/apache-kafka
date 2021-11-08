package br.com.aquila.ecommerce;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Order {
    private final String userId, orderId;
    private final BigDecimal amount;
}
