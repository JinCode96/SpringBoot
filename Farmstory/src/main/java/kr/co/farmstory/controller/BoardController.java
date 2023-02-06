package kr.co.farmstory.controller;

import kr.co.farmstory.service.BoardService;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private BoardService service;

    // 글 목록
    @GetMapping("board/list")
    public String list(Model model, String group, @RequestParam("cate") String cate, String pg){

        // UserEntity user = myUser.getUser();
        // log.info(user.toString());

        int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);
        long total = service.getTotalCount(cate);
        int lastPage = service.getLastPageNum(total);
        int pageStartNum = service.getPageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPage);

        List<ArticleVO> articles = service.selectArticles(cate, start);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        // model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);

        return "board/list";
    }

    // 글 수정
    @GetMapping("board/modify")
    public String modify(String group, String cate, int no, Model model){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        model.addAttribute("no", no);
        return "board/modify";
    }
    @PostMapping("board/modify")
    public String modify(String group, String cate, int no, ArticleVO vo){
        service.updateArticle(vo);
        System.out.println("group : " + group + " cate : " + cate);
        return "redirect:/board/view?group=" + group + "&cate=" + cate + "&no=" + no;
    }

    // 글 쓰기
    @GetMapping("board/write")
    public String write(String group, String cate, Model model){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/write";
    }
    @PostMapping("board/write")
    public String write(ArticleVO vo, String group, String cate){
        // System.out.println("vo : " + vo);
        service.insertArticle(vo);
        return "redirect:/board/list?group=" + group + "&cate=" + cate;
    }

    // 글보기
    @GetMapping("board/view")
    public String view(@RequestParam("no") int no, Model model, String group, String cate){
        ArticleVO article = service.selectArticle(no);
        model.addAttribute("article", article);
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/view";
    }

    // 글 삭제
    @GetMapping("board/delete")
    public String delete(@RequestParam("no") int no, String group, String cate){
        service.deleteArticle(no);
        return "redirect:/board/list?group=" + group + "&cate=" + cate;
    }

    // 파일 다운로드
    @GetMapping("board/download")
    public ResponseEntity<Resource> download(int fno) throws IOException {
        FileVO vo = service.selectFile(fno);
        ResponseEntity<Resource> respEntity = service.fileDownload(vo);
        return respEntity;
    }




}