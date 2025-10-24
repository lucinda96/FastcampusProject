package com.fastcampus.board.dto.response;

import com.fastcampus.board.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostSearchResponse {

    private List<PostDTO> posts;
}
