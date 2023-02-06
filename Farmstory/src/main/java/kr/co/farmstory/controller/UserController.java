package kr.co.farmstory.controller;

import kr.co.farmstory.service.UserService;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService service;

    // 로그인 화면
    @GetMapping("user/login")
    public String login(String success, Model model) {
        model.addAttribute("success", success);
        return "user/login";
    }

    // 회원가입
    @GetMapping("user/register")
    public String register() {
        return "user/register";
    }
    @PostMapping("user/register")
    public String register(UserVO vo, HttpServletRequest req) {
        vo.setRegip(req.getRemoteAddr());
        int result = service.insertUser(vo);
        return "redirect:/user/login?success="+result;
    }

    // 약관 출력
    @GetMapping("user/terms")
    public String terms(Model model) {
        TermsVO termsVO = service.selectTerms();
        model.addAttribute("termsVO", termsVO);
        return "user/terms";
    }

    // 아이디 유효성 검사
    @ResponseBody
    @GetMapping("user/checkUid")
    public Map<String, Integer> checkUid(@RequestParam("uid") String uid) {
        // System.out.println("uid : " + uid);
        Integer result = service.countByUid(uid);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    // 닉네임 유효성 검사
    @ResponseBody
    @GetMapping("user/checkNick")
    public Map<String, Integer> checkNick(@RequestParam("nick") String nick) {
        // System.out.println("nick : " + nick);
        Integer result = service.countByNick(nick);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    // 휴대폰 유효성 검사
    @ResponseBody
    @GetMapping("user/checkHp")
    public Map<String, Integer> checkHp(@RequestParam("hp") String hp) {
        Integer result =  service.countByHp(hp);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

}
