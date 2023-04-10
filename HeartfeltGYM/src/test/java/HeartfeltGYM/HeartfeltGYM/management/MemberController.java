package HeartfeltGYM.HeartfeltGYM.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.List;

// 전제조건, 멤버 DTO 및 멤버서비스 가 있다는 전제
// index.html 에 링크 생성해두기. member/save && member/login && member , 멤버는 회원목록.

@Controller
public class MemberController {

//   MemberService
    // 생성사 주입 받을거임.
    private final MemberService memberService;


    // 회원가입 페이지 출력 요청할거
    // 화면만 필요한거
    @GetMapping("/member/save")
    public String saveForm(){
        return "save";
    }
    // db랑 연동할거
    @GetMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        // db랑 연동하니까 서비스 레포지토리를 거쳐야지만 되게끔 구현한거임임        memberService.save * memberDTO;
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    /*
        1. 회원이 입력한 이메일로 db 에서 조회하고
        2. db 에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치한ㅡㄴㄴ지 판단할거
     */

    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        // 로그인 성공
        if (loginResult != null) {
            session.setAttribute("lgoinEmail", loginResult.getMemberEmail());
            return "main";
        }
        // 로그인 시ㄹ패
        else {
            return "login";
        }
    }
    // 회원 목록을 처리하는 방법
    @GetMapping("/member/")
    public String findAll(){
//      넘겨주는 파라미터가 없고, 아직은 디비 전체값을 끌어옴.
        List<MemberDTO> memberDTOList = memberService.findAll(); // 가져오ㅏ야하니까 리턴을 해야하는데, (sjMun09) 회원은 여러개의 데이터를 가져오니까 List<> 를 써서 처리함.

        model.addAttribute("memberList", memberDTOList);
        return "list";

        // 이후, 서비스 클래스에서 만들어주면 될 것 같음.

    }
}

