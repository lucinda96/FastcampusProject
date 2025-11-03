package com.fastcampus.board.service.impl;

import com.fastcampus.board.dto.CommentDTO;
import com.fastcampus.board.dto.PostDTO;
import com.fastcampus.board.dto.TagDTO;
import com.fastcampus.board.dto.UserDTO;
import com.fastcampus.board.mapper.CommentMapper;
import com.fastcampus.board.mapper.PostMapper;
import com.fastcampus.board.mapper.TagMapper;
import com.fastcampus.board.mapper.UserProfileMapper;
import com.fastcampus.board.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

    public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper, CommentMapper commentMapper, TagMapper tagMapper) {
        this.postMapper = postMapper;
        this.userProfileMapper = userProfileMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if(memberInfo != null){
            try {
                postMapper.register(postDTO);
                Integer postId = postDTO.getId();
                for(TagDTO tag : postDTO.getTagDTOList()){
                    tagMapper.register(tag);
                    Integer tagId = tag.getId();
                    tagMapper.createPostTag(tagId, postId);
                }
            } catch (RuntimeException e) {
                log.error("register ERROR {}", postDTO);
                throw new RuntimeException("register ERROR! 게시물 등록 메서드를 확인해주세요" + postDTO);
            }

        }else{
            log.error("register ERROR {}", postDTO);
            throw new RuntimeException("register ERROR! 게시물 등록 메서드를 확인해주세요" + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postMapper.selectMyPosts(accountId);
        }catch (RuntimeException e){
            log.error("getMyPosts ERROR {}", accountId);
            throw new RuntimeException("getMyPosts ERROR! 게시물 조회 메서드를 확인해주세요" + accountId);
        }
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        postDTO.setUpdateTime(new Date());
        if(postDTO !=null && postDTO.getId() > 0){
            try {
                postMapper.updatePosts(postDTO);
            }catch (RuntimeException e){
                log.error("updatePosts ERROR {}", postDTO);
                throw new RuntimeException("updatePosts ERROR! 게시물 수정 메서드를 확인해주세요" + postDTO);
            }
        }else{
            log.error("updatePosts ERROR {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! 게시물 수정 메서드를 확인해주세요" + postDTO);
        }
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId > 0 && postId > 0){
            try {
                postMapper.deletePosts(postId);
            } catch (RuntimeException e) {
                log.error("deletePosts ERROR {}", postId);
                throw new RuntimeException("deletePosts ERROR! 게시물 삭제 메서드를 확인해주세요" + postId);
            }
        }else {
            log.error("deletePosts ERROR {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시물 삭제 메서드를 확인해주세요" + postId);
        }
    }

    @Override
    public void registerComment(CommentDTO commentDTO) {

        if(commentDTO.getPostId() != 0){
            commentMapper.register(commentDTO);
        }else{
            log.error("registerComment ERROR {}", commentDTO);
            throw new RuntimeException("registerComment " + commentDTO);
        }

    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO != null){
            commentMapper.updateComments(commentDTO);
        }else{
            log.error("updateComment ERROR");
            throw new RuntimeException("updateComment");
        }
    }

    @Override
    public void deletePostComment(int userId, int commentId) {

        if(userId > 0 && commentId > 0){
            commentMapper.deletePostComment(commentId);
        }else{
            log.error("deletePostComment ERROR {}",commentId);
            throw new RuntimeException("deletePostComment "+commentId);
        }

    }

    @Override
    public void registerTag(TagDTO tagDTO) {

        if(tagDTO != null){
            tagMapper.register(tagDTO);
        }else{
            log.error("registerTag ERROR");
            throw new RuntimeException("registerTag ");
        }

    }

    @Override
    public void updateTag(TagDTO tagDTO) {

        if(tagDTO != null){
            tagMapper.updateTags(tagDTO);
        }else{
            log.error("updateTag ERROR");
            throw new RuntimeException("updateTag ");
        }

    }

    @Override
    public void deleteTag(int userId, int tagId) {
        if(userId > 0 && tagId > 0){
            tagMapper.deletePostTag(tagId);
        }else{
            log.error("deleteTag ERROR {}",tagId);
            throw new RuntimeException("deleteTag "+tagId);
        }
    }
}
