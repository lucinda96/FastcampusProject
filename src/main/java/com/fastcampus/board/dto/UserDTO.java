package com.fastcampus.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {

    public static boolean hasNullDataBeforeRegister(UserDTO user) {
        return user.getUserId() == null || user.getPassword() == null || user.getNickName() == null;
    }

    public enum Status{
        DEFAULT, ADMIN, DELETE
    }

    private int id;
    private String userId;
    private String password;
    private String nickName;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw;
    private Status status;
    private Date updateTime;

}
