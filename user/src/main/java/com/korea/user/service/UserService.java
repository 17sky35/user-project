package com.korea.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.korea.user.dto.UserDTO;
import com.korea.user.model.UserEntity;
import com.korea.user.persistence.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	//id중복체크
	public boolean isIdDuplicated(String userId) {
		return !userRepository.findByUserId(userId).isPresent();
	}
	
	//회원추가
	public List<UserDTO> insert(UserDTO dto){
		//dto -> entity
		UserEntity entity = UserDTO.toEntity(dto);
		//entity를 db에 저장
		userRepository.save(entity);
		List<UserEntity> list = userRepository.findAll();
		//List<UserEntity> -> List<UserDTO>
		return list.stream().map(UserDTO::new).collect(Collectors.toList());
	}
	
	//유저 전체조회
	public List<UserDTO> allUsers(){
		List<UserEntity> list = userRepository.findAll();
		return list.stream().map(UserDTO::new).collect(Collectors.toList());
	}
	
	//로그인
	//아이디랑 비밀번호를 받는다.
	public UserEntity getByCredential(String userId, String pwd) {
		return userRepository.findByUserIdAndPwd(userId,pwd);
	}
	
	//userId로 userName받기
	public UserEntity getUserName(String userId) {
		Optional<UserEntity> option = userRepository.findByUserId(userId);
		
		if(option.isPresent()) {
			UserEntity entity = option.get();
			return entity;
		}
		return null;
	}
	
	//수정하기
	public List<UserDTO> save(UserDTO dto) {
		Optional<UserEntity> option = userRepository.findById(dto.getIdx());
		if(option.isPresent()) {
			UserEntity entity = option.get();
			userRepository.save(entity);
			List<UserEntity> list = userRepository.findAll();
			return list.stream().map(UserDTO::new).collect(Collectors.toList());
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
