package com.korea.user.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korea.user.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	//아이디를 기준으로 유저를 검색하는 메서드
	//회원가입할 때 중복체크할 때 쓸거임
	Optional<UserEntity> findByUserId(String userId);

	UserEntity findByUserIdAndPwd(String userId, String pwd);
	
}
