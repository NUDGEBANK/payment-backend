package com.nudgebank.paymentbackend.category.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "market")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long marketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MarketCategory category;

    @Column(name = "market_name", length = 100)
    private String marketName;

    @OneToMany(mappedBy = "market", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private List<MarketMenu> menus = new ArrayList<>();
}
