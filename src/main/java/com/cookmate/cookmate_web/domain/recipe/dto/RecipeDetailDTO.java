package com.cookmate.cookmate_web.domain.recipe.dto;

import com.cookmate.cookmate_web.domain.recipe.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @file        RecipeDetailDTO.java
 * @description 레시피 상세 DTO
 * @author      강보람
 * @since       2026-01-25
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-25      강보람       최초 생성
 * </pre>
 */
 
public class RecipeDetailDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String recipeId;
        private String recipeTtl;
        private String dishNm;
        private String recipeCn;
        private Integer cookingTime;
        private String recipeDifficultCd;
        private String categoryCd;
        private Integer viewCnt;
        private String writerName;
        private LocalDateTime regDt;
        private String recipeStatus;

        private List<String> mainImageUrls;

        private List<IngredientResponse> ingredients;
        private List<StepResponse> steps;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientResponse {
        private String ingrdNm;
        private Float ingrdAmt;
        private String ingrdUnt;

        // Entity -> DTO 변환 메서드
        public static IngredientResponse from(Ingredient entity) {
            return IngredientResponse.builder()
                    .ingrdNm(entity.getIngrdNm())
                    .ingrdAmt(entity.getIngrdAmt())
                    .ingrdUnt(entity.getIngrdUnt())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StepResponse {
        private Integer stepNo;
        private String stepCn;

        private List<String> stepImageUrls;
    }
}
