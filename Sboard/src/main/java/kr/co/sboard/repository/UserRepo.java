package kr.co.sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sboard.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {

	// public int countUserEntityByUid(String uid); // entity 이름 생략 가능
	
	public int countByUid(String uid);
	
}
