package com.nudgebank.paymentbackend.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "market_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Column(name = "menu_name", length = 100)
    private String menuName;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;
}
