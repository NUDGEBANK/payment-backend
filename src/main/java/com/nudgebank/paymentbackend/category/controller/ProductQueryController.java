package com.nudgebank.paymentbackend.category.controller;

import com.nudgebank.paymentbackend.category.dto.CategoryMarketMenuResponse;
import com.nudgebank.paymentbackend.category.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping("/categories-markets-menus")
    public ResponseEntity<CategoryMarketMenuResponse> getAllCategoriesMarketsMenus() {
        CategoryMarketMenuResponse response = productQueryService.getAllCategoriesWithMarketsAndMenus();
        return ResponseEntity.ok(response);
    }
}
