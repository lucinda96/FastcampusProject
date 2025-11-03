package com.fastcampus.board.service.impl;

import com.fastcampus.board.dto.CategoryDTO;
import com.fastcampus.board.mapper.CategoryMapper;
import com.fastcampus.board.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if(accountId != null){
            try {
                categoryMapper.register(categoryDTO);
            }catch (RuntimeException e){
                log.error("register ERROR! {}", categoryDTO);
                throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메소드를 확인해주세요" + categoryDTO);
            }
        }else{
            log.error("register ERROR! {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메소드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if(categoryDTO != null && categoryDTO.getName() != null){
            try {
                categoryMapper.updateCategory(categoryDTO);
            }catch (RuntimeException e){
                log.error("register ERROR! {}", categoryDTO);
                throw new RuntimeException("register ERROR! 게시글 카테고리 수정 메소드를 확인해주세요" + categoryDTO);
            }
        }else{
            log.error("update ERROR! {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 수정 메소드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void delete(int categoryId) {

        if(categoryId !=0){
            try {
                categoryMapper.deleteCategory(categoryId);
            }catch (RuntimeException e){
                log.error("update ERROR! {}", categoryId);
                throw new RuntimeException("register ERROR! 게시글 카테고리 삭제 메소드를 확인해주세요" + categoryId);
            }
        }else{
            log.error("update ERROR! {}", categoryId);
            throw new RuntimeException("register ERROR! 게시글 카테고리 삭제 메소드를 확인해주세요" + categoryId);
        }
    }
}
