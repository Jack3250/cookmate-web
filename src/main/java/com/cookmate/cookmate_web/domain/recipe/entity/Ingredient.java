package com.cookmate.cookmate_web.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @file        Ingredient.java
 * @description 레시피 재료 엔티티
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
@Table(name = "ingredient")
@EntityListeners(AuditingEntityListener.class)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingrd_seq")
    private Long ingrdSeq;

    @Column(name = "ingrd_nm", nullable = false, length = 100)
    private String ingrdNm;

    @Column(name = "ingrd_amt")
    private Float ingrdAmt;

    @Column(name = "ingrd_unt", length = 20)
    private String ingrdUnt;

    @CreatedDate
    @Column(name = "cret_dt", nullable = false, updatable = false)
    private LocalDateTime cretDt;

    // 레시피 조인
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_seq", nullable = false)
    private Recipe recipe;

}
