package com.nudgebank.paymentbackend.category.service;

import com.nudgebank.paymentbackend.category.domain.MarketCategory;
import com.nudgebank.paymentbackend.category.dto.CategoryMarketMenuResponse;
import com.nudgebank.paymentbackend.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final CategoryRepository consumerCategoryRepository;

    public CategoryMarketMenuResponse getAllCategoriesWithMarketsAndMenus() {
        List<MarketCategory> categories = consumerCategoryRepository.findAllWithMarketsAndMenus();
        return CategoryMarketMenuResponse.from(categories);
    }
}
