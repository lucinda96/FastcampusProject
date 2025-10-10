package com.fastcampus.board.aop;

import com.fastcampus.board.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
@Log4j2
public class LoginCheckAspect {

    @Around("@annotation(com.fastcampus.board.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = (HttpSession)((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String id = null;
        int idIndex = 0;

        String userType = loginCheck.type().toString();
        switch (userType) {
            case "ADMIN":
                id= SessionUtil.getLoginAdminId(session);
                break;
            case "USER":
                id = SessionUtil.getLoginMemberId(session);
                break;
        }

        if(id==null){
            log.error(joinPoint.toString()+"accuntName : " + id);
            throw new HttpStatusCodeException(HttpStatus.FORBIDDEN,"로그인한 ID 값을 확인해주세요."){};
        }

        Object[] modefiedArgs = joinPoint.getArgs();

        if(joinPoint != null){
            modefiedArgs[idIndex] = id;
        }

        return joinPoint.proceed(modefiedArgs);
    }
}
