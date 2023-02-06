package kr.co.farmstory.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleVO {

    private int no;
    private int parent;
    private int comment;
    private String cate;
    private String title;
    private String content;
    private int file;
    private MultipartFile fname;
    private int hit;
    private String uid;
    private String regip;
    private String rdate;

    // 추가필드
    private String nick;
    // FileVO를 바로 선언
    private FileVO fileVO; // 여러개의 파일이면 list로
}
