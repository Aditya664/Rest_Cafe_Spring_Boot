package com.cafe.app.InnCafe.serviceimpl;

import com.cafe.app.InnCafe.constant.CafeConstants;
import com.cafe.app.InnCafe.dao.IUserDao;
import com.cafe.app.InnCafe.pojo.User;
import com.cafe.app.InnCafe.service.IUserService;
import com.cafe.app.InnCafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        Logger logger = LoggerFactory.getLogger(UserService.class);
        logger.info("Inside signup{}",requestMap);
       try{
           if(validateSignUpMap(requestMap)){
               User user = userDao.findByEmailId(requestMap.get("email"));
               System.out.println(user);
               if(Objects.isNull(user)){
                   userDao.save(getUserFromMap(requestMap));
                   return CafeUtils.getResponseEntity("Registered successfully", HttpStatus.OK);
               }else{
                   return CafeUtils.getResponseEntity("Email "+CafeConstants.ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
               }
           }else{
               return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
           }
       }catch (Exception ex){
           ex.printStackTrace();
       }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }else{
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setRole(requestMap.get("user"));
        user.setStatus(requestMap.get("status"));
        return user;
    }
}
