package com.cookmate.cookmate_web.domain.recipe.dto;

import com.cookmate.cookmate_web.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @file        RecipeDTO.java
 * @description 레시피 요청 응답 DTO
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

public class RecipeDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String recipeTtl;
        private String dishNm;
        private String recipeCn;
        private Integer cookingTime;
        private String recipeDifficultCd;
        private String categoryCd;
        private String fileGrpId;
        private String recipeStatus;
        private List<IngredientRequest> ingredients;
        private List<StepRequest> steps;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IngredientRequest {
        private String ingrdNm;
        private Float ingrdAmt;
        private String ingrdUnt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StepRequest {
        private Integer stepNo;
        private String stepCn;
    }
}