package com.fastcampus.board.service.impl;

import com.fastcampus.board.dto.UserDTO;
import com.fastcampus.board.exception.DuplicateIdException;
import com.fastcampus.board.mapper.UserProfileMapper;
import com.fastcampus.board.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fastcampus.board.utils.SHA256Util.encryptSHA256;

@Service
@Log4j2
public class UserServiceImpl implements UserService {



    private final UserProfileMapper userProfileMapper;

    @Autowired
    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(UserDTO userProfile) {
        boolean dupleIdResult = isDuplicatedId(userProfile.getUserId());
        if(dupleIdResult){
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userProfile.setCreateTime(new java.util.Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));

        int insertCount = userProfileMapper.insertUserProfile(userProfile);
        if(insertCount != 1){
            log.error("insertMember ERROR! {}", userProfile);
            throw new RuntimeException("insertMember ERROR!");
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        return userProfileMapper.findByIdAndPassword(id, cryptPassword);
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptPassword = encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);

        if(memberInfo != null){
            memberInfo.setPassword(encryptSHA256(afterPassword));
            userProfileMapper.updatePassword(memberInfo);
        }else{
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지않습니다");
        }
    }

    @Override
    public void deleteId(String id, String password) {

        String cryptPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);

        if(memberInfo != null){
            userProfileMapper.deleteUserProfile(id);
        }else{
            log.error("delete ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지않습니다");
        }
    }
}
