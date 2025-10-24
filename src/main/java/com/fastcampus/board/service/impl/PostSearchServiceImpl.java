package com.fastcampus.board.service.impl;

import com.fastcampus.board.dto.PostDTO;
import com.fastcampus.board.dto.request.PostSearchRequest;
import com.fastcampus.board.mapper.PostSearchMapper;
import com.fastcampus.board.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper) {
        this.postSearchMapper = postSearchMapper;
    }

    @Cacheable(value = "getPosts", unless="#result == null", key="'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPosts(postSearchRequest);
        }catch (RuntimeException e){
            log.error("selectPosts 메서드 실패", e.getMessage());
        }
        return postDTOList;
    }

}
