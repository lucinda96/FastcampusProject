package com.fastcampus.board.mapper;

import com.fastcampus.board.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {

    int register(TagDTO tagDTO);
    void updateTags(TagDTO tagDTO);
    void deletePostTag(int tagId);
    void createPostTag(Integer tagId,Integer postId);

}
