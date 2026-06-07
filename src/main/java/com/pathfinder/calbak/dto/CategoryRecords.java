package com.pathfinder.calbak.dto;

import com.pathfinder.calbak.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class CategoryRecords {

    public record UpdateCategoryRequest(
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        String name,

        @NotBlank(message = "색상 코드는 필수입니다.")
        String colorCode
    ) {
    }

    public record CategoryResponse(
        UUID id,
        String name,
        String colorCode
    ) {
        public static CategoryResponse from(Category category) {
            return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getColorCode()
            );
        }
    }
}
