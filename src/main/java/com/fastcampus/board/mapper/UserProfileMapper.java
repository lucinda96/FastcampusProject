package com.fastcampus.board.mapper;

import com.fastcampus.board.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(@Param("id") String id);

    int insertUserProfile(UserDTO user);

    int deleteUserProfile(@Param("id") String id);

    UserDTO findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(@Param("id") String id);

    int updatePassword(UserDTO user);

    int updateAddress(UserDTO user);

}
