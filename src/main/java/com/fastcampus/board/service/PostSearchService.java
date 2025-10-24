package com.fastcampus.board.service;

import com.fastcampus.board.dto.PostDTO;
import com.fastcampus.board.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);
}
