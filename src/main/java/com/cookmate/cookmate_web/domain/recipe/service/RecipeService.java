package com.cookmate.cookmate_web.domain.recipe.service;

import com.cookmate.cookmate_web.domain.common.util.KeygenUtil;
import com.cookmate.cookmate_web.domain.file.service.FileService;
import com.cookmate.cookmate_web.domain.recipe.dto.RecipeDTO;
import com.cookmate.cookmate_web.domain.recipe.entity.Ingredient;
import com.cookmate.cookmate_web.domain.recipe.entity.Recipe;
import com.cookmate.cookmate_web.domain.recipe.entity.RecipeStep;
import com.cookmate.cookmate_web.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @file        RecipeService.java
 * @description 레시피 관리 서비스
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

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
//    private final UserRepository userRepository;
    private final FileService fileService;

    /**
     * 레시피 등록
     * @param loginId       로그인 ID
     * @param request       저장 요청 데이터
     * @param mainImage         메인 사진
     * @param stepImagesMap 단계별 사진 목록
     * @return 레시피 ID
     */
    @Transactional
    public String saveRecipe(String loginId,
                             RecipeDTO.Request request,
                             List<MultipartFile> mainImage,
                             Map<Integer, List<MultipartFile>> stepImagesMap) {
        /*
        TODO : 작성자 조회 필요
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
         */
        String rgtrKey = "testuserkey";

        // 파일 저장
        String mainFileGrpId = null;
        if (mainImage != null && !mainImage.isEmpty()) {
            mainFileGrpId = fileService.saveFile(mainImage, null, rgtrKey);
        }

        // 레시피 엔티티 생성
        Recipe recipe = Recipe.builder()
                .recipeId(KeygenUtil.generateKey())
                .recipeTtl(request.getRecipeTtl())
                .dishNm(request.getDishNm())
                .recipeCn(request.getRecipeCn())
                .cookingTime(request.getCookingTime())
                .recipeDifficultCd(request.getRecipeDifficultCd())
                .categoryCd(request.getCategoryCd())
                .fileGrpId(mainFileGrpId)
//                .user(user)
                .rgtrKey(rgtrKey)
                .recipeStatus(request.getRecipeStatus())
                .viewCnt(0)
                .delYn("N")
                .build();

        // 재료 추가
        if (request.getIngredients() != null) {
            for (RecipeDTO.IngredientRequest dto : request.getIngredients()) {
                recipe.addIngredient(Ingredient.builder()
                        .ingrdNm(dto.getIngrdNm())
                        .ingrdAmt(dto.getIngrdAmt())
                        .ingrdUnt(dto.getIngrdUnt())
                        .build());
            }
        }

        // 조리 단계 및 단계별 사진 추가
        if (request.getSteps() != null) {
            List<RecipeDTO.StepRequest> steps = request.getSteps();

            for (int i = 0; i < steps.size(); i++) {
                RecipeDTO.StepRequest step = steps.get(i);

                // 해당 순서 이미지 확인
                String stepFileGrpId = null;
                if (stepImagesMap.containsKey(i)) {
                    stepFileGrpId = fileService.saveFile(stepImagesMap.get(i), null, rgtrKey);
                }

                recipe.addStep(RecipeStep.builder()
                        .stepNo(step.getStepNo())
                        .stepCn(step.getStepCn())
                        .fileGrpId(stepFileGrpId)
                        .build());
            }
        }

        return recipeRepository.save(recipe).getRecipeId();
    }

    /**
     * 레시피 목록 조회
     * @return 레시피 목록
     */
    @Transactional(readOnly = true)
    public List<RecipeDTO.Response> findAllRecipe() {
        List<RecipeDTO.Response> result = new ArrayList<>();

        // 삭제되지 않은 레시피 최신순 조회
        List<Recipe> recipeList = recipeRepository.findByDelYnOrderByRecipeSeqDesc("N");

        if (!recipeList.isEmpty()) {
            for(Recipe recipe : recipeList) {
                // 대표 이미지 URL 가져오기
                List<String> urls = fileService.getFileUrls(recipe.getFileGrpId());

                String mainImageUrl = null;
                if (!urls.isEmpty()) {
                    mainImageUrl = urls.get(0);
                }

                // DTO 변환
                RecipeDTO.Response response = RecipeDTO.Response.from(recipe, mainImageUrl);
                result.add(response);
            }
        }

        return result;
    }
    /*
    ========================================================
    헬퍼 메소드
    ========================================================
     */

    /**
     * 단계별 사진 추출
     * @param request 저장 요청 데이터
     * @param multipartRequest 단계별 사진을 추출하기 위한 요청 객체
     * @return 단계별 사진 목록
     */
    public Map<Integer, List<MultipartFile>> extractStepImages(RecipeDTO.Request request, MultipartHttpServletRequest multipartRequest) {
        Map<Integer, List<MultipartFile>> stepImagesMap = new HashMap<>();

        if (request.getSteps() != null) {
            for (int i = 0; i < request.getSteps().size(); i++) {
                String key = "stepImages_" + i;
                List<MultipartFile> files = multipartRequest.getFiles(key);
                if (!files.isEmpty()) {
                    stepImagesMap.put(i, files);
                }
            }
        }

        return stepImagesMap;
    }
}
