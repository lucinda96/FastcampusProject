package com.fastcampus.board.mapper;

import com.fastcampus.board.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    int register(CommentDTO commentDTO);
    void updateComments(CommentDTO commentDTO);
    void deletePostComment(int commentId);
}
