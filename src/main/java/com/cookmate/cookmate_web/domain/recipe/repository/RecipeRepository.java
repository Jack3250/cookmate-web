package com.cookmate.cookmate_web.domain.recipe.repository;

import com.cookmate.cookmate_web.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @file        RecipeRepository.java
 * @description 레시피 리포지토리
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

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * 레시피 목록 조회
     * @param delYn 삭제 여부
     * @return 레시피 목록
     */
    List<Recipe> findByDelYnOrderByRecipeSeqDesc(String delYn);
}