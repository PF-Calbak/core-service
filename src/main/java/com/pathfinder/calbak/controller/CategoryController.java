package com.pathfinder.calbak.controller;

import com.pathfinder.calbak.dto.CategoryRecords.CategoryResponse;
import com.pathfinder.calbak.dto.CategoryRecords.UpdateCategoryRequest;
import com.pathfinder.calbak.service.CategoryService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
        Authentication authentication,
        @PathVariable UUID categoryId,
        @Valid @RequestBody UpdateCategoryRequest request) {

        CategoryResponse response = categoryService.updateCategory(
            categoryId,
            authentication.getName(),
            request.name(),
            request.colorCode()
        );
        return ResponseEntity.ok(response);
    }

    // 중복 이름 에러를 500이 아닌 400 에러로 잡아주는 핸들러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("잘못된 요청입니다: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("서버 에러: " + e.getMessage());
    }
}
