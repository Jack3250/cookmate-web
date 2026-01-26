package com.cookmate.cookmate_web.domain.recipe.dto;

import com.cookmate.cookmate_web.domain.recipe.entity.Ingredient;
import com.cookmate.cookmate_web.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @file        RecipeResponseDTO.java
 * @description 레시피 목록, 상세 응답 DTO
 * @author      강보람
 * @since       2026-01-26
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-26      강보람       최초 생성
 * </pre>
 */
 
public class RecipeResponseDTO {
    // 목록 조회용
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Summary {
        private String recipeId;
        private String recipeTtl;
        private String dishNm;
        private String writerName;
        private Integer viewCnt;
        private String categoryCd;
        private String recipeDifficultCd;
        private LocalDateTime regDt;
        private String mainImageUrl;

        public static Summary from(Recipe recipe, String mainImageUrl) {
            return Summary.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeTtl(recipe.getRecipeTtl())
                    .dishNm(recipe.getDishNm())
                    .writerName(recipe.getRgtrKey()) // 추후 닉네임 변경
                    .viewCnt(recipe.getViewCnt())
                    .categoryCd(recipe.getCategoryCd())
                    .recipeDifficultCd(recipe.getRecipeDifficultCd())
                    .mainImageUrl(mainImageUrl)
                    .regDt(recipe.getRegDt())
                    .build();
        }
    }

    // 상세 정보
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
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
        private List<IngredientDto> ingredients;
        private List<StepDto> steps;
    }

    // 재료 출력
    @Getter
    @Builder
    @AllArgsConstructor
    public static class IngredientDto {
        private String ingrdNm;
        private Float ingrdAmt;
        private String ingrdUnt;

        public static IngredientDto from(Ingredient entity) {
            return IngredientDto.builder()
                    .ingrdNm(entity.getIngrdNm())
                    .ingrdAmt(entity.getIngrdAmt())
                    .ingrdUnt(entity.getIngrdUnt())
                    .build();
        }
    }

    // 단계 출력
    @Getter
    @Builder
    @AllArgsConstructor
    public static class StepDto {
        private Integer stepNo;
        private String stepCn;
        private List<String> stepImageUrls;
    }
}
