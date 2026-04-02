package com.nudgebank.paymentbackend.category.dto;

import com.nudgebank.paymentbackend.category.domain.Market;
import com.nudgebank.paymentbackend.category.domain.MarketCategory;
import com.nudgebank.paymentbackend.category.domain.MarketMenu;

import java.math.BigDecimal;
import java.util.List;

public record CategoryMarketMenuResponse(
        List<CategoryDto> categories
) {
    public static CategoryMarketMenuResponse from(List<MarketCategory> categories) {
        return new CategoryMarketMenuResponse(
                categories.stream()
                        .map(CategoryDto::from)
                        .toList()
        );
    }

    public record CategoryDto(
            Long categoryId,
            String categoryName,
            String mcc,
            List<MarketDto> markets
    ) {
        public static CategoryDto from(MarketCategory category) {
            return new CategoryDto(
                    category.getCategoryId(),
                    category.getCategoryName(),
                    category.getMcc(),
                    category.getMarkets().stream()
                            .map(MarketDto::from)
                            .toList()
            );
        }
    }

    public record MarketDto(
            Long marketId,
            String marketName,
            List<MenuDto> menus
    ) {
        public static MarketDto from(Market market) {
            return new MarketDto(
                    market.getMarketId(),
                    market.getMarketName(),
                    market.getMenus().stream()
                            .map(MenuDto::from)
                            .toList()
            );
        }
    }

    public record MenuDto(
            Long menuId,
            String menuName,
            BigDecimal price
    ) {
        public static MenuDto from(MarketMenu menu) {
            return new MenuDto(
                    menu.getMenuId(),
                    menu.getMenuName(),
                    menu.getPrice()
            );
        }
    }
}
