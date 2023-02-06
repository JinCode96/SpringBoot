package kr.co.farmstory.repository;

import kr.co.farmstory.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<UserEntity, String> {
	// public int countUserEntityByUid(String uid); // entity 이름 생략 가능
	public Integer countUserEntityByUid(String uid);
	public Integer countUserEntityByNick(String nick);
	public Integer countUserEntityByHp(String hp);

}
