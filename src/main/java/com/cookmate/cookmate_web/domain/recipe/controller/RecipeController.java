package com.cookmate.cookmate_web.domain.recipe.controller;

import com.cookmate.cookmate_web.domain.recipe.dto.RecipeDTO;
import com.cookmate.cookmate_web.domain.recipe.service.RecipeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @file        RecipeController.java
 * @description 레시피 컨트롤러
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

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * 레시피 등록
     * @param request 등록 요청 데이터
     * @param mainImage 메인 사진
     * @param multipartRequest 단계별 사진을 추출하기 위한 요청 객체
     * @param session 로그인 세션
     * @return 레시피 ID
     */
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> save(
            @RequestPart(value = "data") RecipeDTO.Request request,
            @RequestPart(value = "mainImage", required = false) List<MultipartFile> mainImage, // 메인 사진
            MultipartHttpServletRequest multipartRequest, // 단계별 사진을 추출하기 위한 요청 객체
            HttpSession session
    ) {
        /*
        TODO : 로그인 세션 적용 시 이용
        SessionUser user = (SessionUser) session.getAttribute("USER_SESSION");
        if (user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        String loginId = user.getLoginId();
         */
        String loginId = "testuser";

        // 단계별 사진 추출
        Map<Integer, List<MultipartFile>> stepImagesMap = recipeService.extractStepImages(request, multipartRequest);

        return ResponseEntity.ok(recipeService.saveRecipe(loginId, request, mainImage, stepImagesMap));
    }

    /**
     * 레시피 목록 조회
     * @return 레시피 목록
     */
    @GetMapping("/list")
    public ResponseEntity<List<RecipeDTO.Response>> findAll() {
        return ResponseEntity.ok(recipeService.findAllRecipe());
    }
}
