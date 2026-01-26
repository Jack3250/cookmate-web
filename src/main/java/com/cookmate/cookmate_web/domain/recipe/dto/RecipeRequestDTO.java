package com.cookmate.cookmate_web.domain.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @file        RecipeRequestDTO.java
 * @description 레시피 등록, 수정 요청 DTO
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
 
public class RecipeRequestDTO {

    // 레시피 등록 요청
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String recipeId;
        private String recipeTtl;
        private String dishNm;
        private String recipeCn;
        private Integer cookingTime;
        private String recipeDifficultCd;
        private String categoryCd;
        private String recipeStatus;

        private String fileGrpId;

        private List<String> deleteFileIds;

        private List<Ingredient> ingredients;
        private List<Step> steps;
    }

    // 재료 입력
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ingredient {
        private String ingrdNm;
        private Float ingrdAmt;
        private String ingrdUnt;
    }

    // 단계 입력
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Step {
        private Integer stepNo;
        private String stepCn;
        private String fileGrpId;

        private List<String> deleteFileIds;
    }
}
