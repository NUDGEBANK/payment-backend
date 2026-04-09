package com.nudgebank.paymentbackend.category.repository;

import com.nudgebank.paymentbackend.category.domain.MarketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<MarketCategory, Long> {

    @Query("""
            select distinct c
            from MarketCategory c
            left join fetch c.markets m
            where c.categoryId <= 16
            order by c.categoryId, m.marketId
            """)
    List<MarketCategory> findAllWithMarketsAndMenus();
}
