package com.nudgebank.paymentbackend.category.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "market_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "mcc")
    private String mcc;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private List<Market> markets = new ArrayList<>();
}
