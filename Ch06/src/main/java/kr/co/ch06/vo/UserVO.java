package kr.co.ch06.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// vo의 기본 어노테이션들
@Getter 			// lombok에서 제공하는 기능
@Setter
@AllArgsConstructor // 모든 args(매개변수)를 가지고 생성자 만듦
@NoArgsConstructor 	// 매개변수 없이 생성자 호출
@ToString 			// 어떤 값으로 저장되었는지 확인할 때 사용되는 메서드
public class UserVO {
	
	private String uid;
	private String name;
	private String hp;
	private int age;

	
	/*
	public UserVO(String uid, String name, String hp, int age) {
		this.uid = uid;
		this.name = name;
		this.hp = hp;
		this.age = age;
	}
	*/
}
