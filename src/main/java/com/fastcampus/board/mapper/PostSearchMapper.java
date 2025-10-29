package com.fastcampus.board.mapper;

import com.fastcampus.board.dto.PostDTO;
import com.fastcampus.board.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {

    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostByTag(String tagName);
}
