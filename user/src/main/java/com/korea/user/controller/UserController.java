package com.korea.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.user.dto.ResponseDTO;
import com.korea.user.dto.UserDTO;
import com.korea.user.model.UserEntity;
import com.korea.user.security.TokenProvider;
import com.korea.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	private final TokenProvider tokenProvider;
	
	//id중복조회
	//POST,PUT,DELETE로 전달하면 데이터들이 RequestBody로 전송
	//GET으로 전달할 때는 RequestBody로 전송되지 않음
	//localhost:9090/users/idCheck?userid='xx'
	//@GetMapping
	//public ResponseEntity<?> isIdDuplicate(@RequestParam("userId") UserDTO dto){
	@PostMapping("/idCheck")
	public ResponseEntity<?> isIdDuplicate(@RequestBody UserDTO dto){
		boolean check = userService.isIdDuplicated(dto.getUserId());
		ResponseDTO<Boolean> response = ResponseDTO.<Boolean>builder().value(check).build();
		return ResponseEntity.ok().body(response);
	}
	
	//회원가입
	@PostMapping("/signup")
	//id
	//pwd
	//name
	//email
	public ResponseEntity<?> sginup (@RequestBody UserDTO dto){
		List<UserDTO> dtos = userService.insert(dto);
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);		
	}
	
	//모든 유저 조회
	@GetMapping("/allUsers")
	public ResponseEntity<?> allUsers(){
		List<UserDTO> dtos = userService.allUsers();
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	//로그인
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO dto){
		//아이디와 비밀번호를 입력받는다.
		UserEntity user = userService.getByCredential(dto.getUserId(), dto.getPwd());
		
		//조회가 됐다면
		if(user != null) {
			//토큰을 발급해준다.
			final String token = tokenProvider.create(user);
			
			final UserDTO response = UserDTO.builder()
											.userId(user.getUserId())
											.idx(user.getIdx())
											.name(user.getName())
											.email(user.getEmail())
											.token(token)
											.build();
			return ResponseEntity.ok().body(response);
		}else {
			ResponseDTO response = ResponseDTO.builder().error("Login failed").build();
			return ResponseEntity.ok().body(response);
		}		
	}
	
	//userName 검색
	@GetMapping("/name")
	//@RequestHeader : HTTP 요청헤더 값을 커트롤러의 메서드에 주입하는데 사용되는 어노테이션
	public ResponseEntity<?> getUserName(@RequestHeader("Authorization") String token){
		//"Bearer " 제거
		String actualToken = token.substring(7);		
		//JWT에서 유저 id추출
		String userId = tokenProvider.validateAndeGetUserId(actualToken);
		UserEntity entity = userService.getUserName(userId);
		//Entity -> DTO
		UserDTO dto = new UserDTO(entity);
		//List에 묶음
		List<UserDTO> dtos = Arrays.asList(dto);
		//ResponseDTO의 data필드에 넣어서 반환
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	//수정하기
	@PutMapping("/modify")
	public void modify(@RequestBody UserDTO dto){
		userService.userModify(dto);
	}
	
	
	
	
}
