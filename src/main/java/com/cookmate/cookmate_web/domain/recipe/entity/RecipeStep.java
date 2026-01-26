package com.cookmate.cookmate_web.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @file        RecipeStep.java
 * @description 레시피 단계 엔티티
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
@Table(name = "recipe_step")
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_seq")
    private Long stepSeq;

    @Column(name = "step_no", nullable = false)
    private Integer stepNo;

    @Column(name = "step_cn", nullable = false, columnDefinition = "TEXT")
    private String stepCn;

    @Column(name = "file_grp_id", length = 20)
    private String fileGrpId;

    // 레시피 조인
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_seq", nullable = false)
    private Recipe recipe;
}
