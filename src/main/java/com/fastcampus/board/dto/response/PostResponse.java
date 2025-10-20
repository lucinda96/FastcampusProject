package com.fastcampus.board.dto.response;

import com.fastcampus.board.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponse {
    private List<PostDTO> posts;
}
