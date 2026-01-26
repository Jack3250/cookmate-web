package com.cookmate.cookmate_web.domain.recipe.service;

import com.cookmate.cookmate_web.domain.common.util.KeygenUtil;
import com.cookmate.cookmate_web.domain.file.service.FileService;
import com.cookmate.cookmate_web.domain.global.error.CustomException;
import com.cookmate.cookmate_web.domain.global.error.ErrorCode;
import com.cookmate.cookmate_web.domain.recipe.dto.RecipeRequestDTO;
import com.cookmate.cookmate_web.domain.recipe.dto.RecipeResponseDTO;
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
                             RecipeRequestDTO.Request request,
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
        addIngredients(request, recipe);

        // 조리 단계 및 단계별 사진 추가
        if (request.getSteps() != null) {
            List<RecipeRequestDTO.Step> steps = request.getSteps();

            for (int i = 0; i < steps.size(); i++) {
                RecipeRequestDTO.Step step = steps.get(i);

                // 해당 순서 이미지 유무 확인 밎 저장
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
    public List<RecipeResponseDTO.Summary> findAllRecipe() {
        List<RecipeResponseDTO.Summary> result = new ArrayList<>();

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
                RecipeResponseDTO.Summary response = RecipeResponseDTO.Summary.from(recipe, mainImageUrl);
                result.add(response);
            }
        }

        return result;
    }

    /**
     * 레시피 상세 조회
     * @param recipeId 레시피 ID
     * @return 레시피 상세 정보
     */
    @Transactional(readOnly = true)
    public RecipeResponseDTO.Detail findRecipeDetail(String recipeId) {
        Recipe recipe = recipeRepository.findByRecipeIdAndDelYn(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        // 메인 이미지 가져오기
        List<String> mainImageUrls = fileService.getFileUrls(recipe.getFileGrpId());

        // 재료 변환
        List<RecipeResponseDTO.IngredientDto> ingredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            RecipeResponseDTO.IngredientDto ingredientDto = RecipeResponseDTO.IngredientDto.from(ingredient);
            ingredients.add(ingredientDto);
        }

        // 조리 단계 변환
        List<RecipeResponseDTO.StepDto> steps = new ArrayList<>();
        for (RecipeStep step : recipe.getRecipeSteps()) {
            steps.add(RecipeResponseDTO.StepDto.builder()
                    .stepNo(step.getStepNo())
                    .stepCn(step.getStepCn())
                    .stepImageUrls(fileService.getFileUrls(step.getFileGrpId()))
                    .build());
        }

        return RecipeResponseDTO.Detail.builder()
                .recipeId(recipe.getRecipeId())
                .recipeTtl(recipe.getRecipeTtl())
                .dishNm(recipe.getDishNm())
                .recipeCn(recipe.getRecipeCn())
                .cookingTime(recipe.getCookingTime())
                .recipeDifficultCd(recipe.getRecipeDifficultCd())
                .categoryCd(recipe.getCategoryCd())
                .writerName(recipe.getRgtrKey()) // TODO : 차후 USER 객체 가져오면 닉네임으로 변경
                .regDt(recipe.getRegDt())
                .mainImageUrls(mainImageUrls)
                .ingredients(ingredients)
                .steps(steps)
                .build();
    }

    /*
    ========================================================
    헬퍼 메소드
    ========================================================
     */

    /**
     * 재료 추가
     * @param request 저장 요청 데이터
     * @param recipe 레시피 엔티티
     */
    private void addIngredients(RecipeRequestDTO.Request request, Recipe recipe) {
        if (request.getIngredients() != null) {
            for (RecipeRequestDTO.Ingredient dto : request.getIngredients()) {
                recipe.addIngredient(Ingredient.builder()
                        .ingrdNm(dto.getIngrdNm())
                        .ingrdAmt(dto.getIngrdAmt())
                        .ingrdUnt(dto.getIngrdUnt())
                        .build());
            }
        }
    }


    /**
     * 단계별 사진 추출
     * @param request 저장 요청 데이터
     * @param multipartRequest 단계별 사진을 추출하기 위한 요청 객체
     * @return 단계별 사진 목록
     */
    public Map<Integer, List<MultipartFile>> extractStepImages(RecipeRequestDTO.Request request,
                                                               MultipartHttpServletRequest multipartRequest) {
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
