package kr.co.farmstory.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.farmstory.dao.BoardDAO;
import kr.co.farmstory.repository.ArticleRepo;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {

    @Autowired
    private BoardDAO dao;

    @Autowired
    private ArticleRepo repo;

    // 글 등록
    @Transactional
    public int insertArticle(ArticleVO vo) {

        // 글 등록
        int result = dao.insertArticle(vo);

        // 파일 업로드
        FileVO fvo = fileUpload(vo);

        if(fvo != null) {
            // 파일 등록
            dao.insertFile(fvo);
        }

        return result;
    }

    // 파일
    @Transactional
    public FileVO selectFile(int fno) {
        FileVO vo = dao.selectFile(fno);
        dao.updateDownload(fno);
        return vo;
    }

    // 글 보기
    public ArticleVO selectArticle(int no) {
        return dao.selectArticle(no);
    }

    // 글 리스트 조회
    public List<ArticleVO> selectArticles(String cate, int start) {
        return dao.selectArticles(cate, start);
    }

    // 글 수정
    public int updateArticle(ArticleVO vo) {
        return dao.updateArticle(vo);
    }
    public int deleteArticle(int no) {
        return dao.deleteArticle(no);
    }

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // 위의 프로퍼티의 벨류값을 uploadPath에 대입

    // 파일 업로드
    public FileVO fileUpload(ArticleVO vo) {

        // 첨부 파일
        MultipartFile file = vo.getFname();
        FileVO fvo = null;

        if(!file.isEmpty()) {
            // 시스템 경로
            String path = new File(uploadPath).getAbsolutePath();

            // 새 파일명 생성
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String nName = UUID.randomUUID().toString()+ext;

            // 파일 저장
            try {
                file.transferTo(new File(path, nName));
            } catch (IllegalStateException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            fvo = FileVO.builder()
                    .parent(vo.getNo())
                    .oriName(oName)
                    .newName(nName)
                    .build();
        }

        return fvo;
    }

    // 파일 다운로드
    public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {

        Path path = Paths.get(uploadPath + vo.getNewName()); // 다운로드하려는 파일의 경로

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(vo.getOriName(), StandardCharsets.UTF_8)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }



    //////////// 페이징 처리 /////////////

    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(String pg) {
        int currentPage = 1;

        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    // 전체 게시물 갯수
    public long getTotalCount(String cate) {
        return dao.selectCountTotal(cate);
    }

    // 마지막 페이지 번호
    public int getLastPageNum(long total) {

        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = (int) (total / 10);
        }else {
            lastPage = (int) (total / 10) + 1;
        }
        return lastPage;
    }

    // 페이지 시작 번호
    public int getPageStartNum(long total, int start) {
        return (int) (total - start);
    }

    // 페이지 그룹
    public int[] getPageGroup(int currentPage, int lastPage) {
        int groupCurrent = (int) Math.ceil(currentPage / 10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;

    }

}
