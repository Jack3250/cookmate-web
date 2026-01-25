package com.cookmate.cookmate_web.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @file        Recipe.java
 * @description 레시피 엔티티
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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "recipe")
@EntityListeners(AuditingEntityListener.class)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_seq")
    private Long recipeSeq;

    @Column(name = "recipe_id", nullable = false, length = 20, unique = true)
    private String recipeId;

    @Column(name = "recipe_ttl", nullable = false, length = 100)
    private String recipeTtl;

    @Column(name = "dish_nm", nullable = false, length = 100)
    private String dishNm;

    @Column(name = "recipe_cn", nullable = false, columnDefinition = "TEXT")
    private String recipeCn;

    @Column(name = "cooking_time", nullable = false)
    @ColumnDefault("0")
    private Integer cookingTime = 0;

    @Column(name = "recipe_difficult_cd", nullable = false, length = 20)
    private String recipeDifficultCd;

    @Column(name = "category_cd", nullable = false, length = 20)
    private String categoryCd;

    @Column(name = "view_cnt", nullable = false)
    @ColumnDefault("0")
    private Integer viewCnt = 0;

    @Column(name = "file_grp_id", length = 20)
    private String fileGrpId;

    @CreatedDate
    @Column(name = "reg_dt", nullable = false, updatable = false)
    private LocalDateTime regDt;

    @Column(name = "mdfr_key", length = 20)
    private String mdfrKey;

    @LastModifiedDate
    @Column(name = "mdfcn_dt")
    private LocalDateTime mdfcnDt;

    @Column(name = "del_yn", nullable = false, length = 1)
    @ColumnDefault("'N'")
    @Builder.Default
    private String delYn = "N";

    @Column(name = "recipe_status", nullable = false, length = 20)
    private String recipeStatus;

    // TODO : 회원 조인 필요
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "rgtr_key", referencedColumnName = "user_key")
//    private User user;

    private String rgtrKey;

    // 재료 조인
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();

    // 조리 단계 조인
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RecipeStep> recipeSteps = new ArrayList<>();

    // 재료 추가
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    // 단계 추가
    public void addStep(RecipeStep step) {
        this.recipeSteps.add(step);
        step.setRecipe(this);
    }
}