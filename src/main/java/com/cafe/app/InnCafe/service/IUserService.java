package com.cafe.app.InnCafe.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IUserService {

     ResponseEntity<String> signUp(Map<String, String> requestMap);
}
