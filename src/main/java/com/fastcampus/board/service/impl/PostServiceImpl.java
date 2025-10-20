package com.fastcampus.board.service.impl;

import com.fastcampus.board.dto.PostDTO;
import com.fastcampus.board.dto.UserDTO;
import com.fastcampus.board.mapper.PostMapper;
import com.fastcampus.board.mapper.UserProfileMapper;
import com.fastcampus.board.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;

    public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper) {
        this.postMapper = postMapper;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if(memberInfo != null){
            postMapper.register(postDTO);
        }else{
            log.error("register ERROR {}", postDTO);
            throw new RuntimeException("register ERROR! 게시물 등록 메서드를 확인해주세요" + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        return postMapper.selectMyPosts(accountId);
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        postDTO.setUpdateTime(new Date());
        if(postDTO !=null && postDTO.getId() > 0){
            postMapper.updatePosts(postDTO);
        }else{
            log.error("updatePosts ERROR {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! 게시물 수정 메서드를 확인해주세요" + postDTO);
        }
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId > 0 && postId > 0){
            postMapper.deletePosts(postId);
        }else {
            log.error("deletePosts ERROR {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시물 삭제 메서드를 확인해주세요" + postId);
        }
    }
}
