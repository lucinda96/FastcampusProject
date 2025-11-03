package com.fastcampus.board.service.impl;

import com.fastcampus.board.dto.PostDTO;
import com.fastcampus.board.dto.request.PostSearchRequest;
import com.fastcampus.board.exception.BoardServerException;
import com.fastcampus.board.mapper.PostSearchMapper;
import com.fastcampus.board.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper) {
        this.postSearchMapper = postSearchMapper;
    }

    @Async
    @Cacheable(value = "getPosts", unless="#result == null", key="'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPosts(postSearchRequest);
        }catch (RuntimeException e){
            log.error("getPosts 메서드 실패", e.getMessage());
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostByTag(String tagName) {

        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostByTag(tagName);
        }catch (RuntimeException e){
            log.error("getPostByTag 메소드 실패",e.getMessage());
        }

        return postDTOList;
    }

}
