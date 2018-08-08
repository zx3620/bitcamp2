// 로그인 폼 출력과 사용자 인증처리 서블릿
package bitcamp.java106.pms.web.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import bitcamp.java106.pms.domain.Member;
import bitcamp.java106.pms.service.MemberService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    MemberService memberService;
     
    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    @RequestMapping("/form")
    public void form() {
    }
            
    @GetMapping("/loginUser")
    public Member loginUser(HttpSession session) {
        return (Member)session.getAttribute("loginUser");
    }
    
    @RequestMapping("/login")
    public Object login(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam(value="saveId",required=false) String saveId,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        
        Cookie cookie = null;
        if (saveId != null) {
            // 입력폼에서 로그인할 때 사용한 ID를 자동으로 출력할 수 있도록 
            // 웹브라우저로 보내 저장시킨다.
            cookie = new Cookie("id", id);
            cookie.setMaxAge(60 * 60 * 24 * 7);
        } else { // "아이디 저장" 체크박스를 체크하지 않았다면 
            cookie = new Cookie("id", "");
            cookie.setMaxAge(0); // 웹브라우저에 "id"라는 이름으로 저장된 쿠키가 있다면 제거한다.
            // 즉 유효기간을 0으로 설정함으로써 "id"라는 이름의 쿠키를 무효화시키는 것이다.
        }
        response.addCookie(cookie);
        
        HashMap<String,Object> result = new HashMap<>();
        
        if (memberService.isExist(id, password)) { // 로그인 성공!
            session.setAttribute("loginUser", memberService.get(id));
            result.put("state", "success");

        } else { // 로그인 실패!
            session.invalidate();
            result.put("state", "fail");
        }
        return result;
    }
    
    @RequestMapping("/logout")
    public void logout(HttpSession session) throws Exception {
        
        session.invalidate();
        
    }
    
    @GetMapping("/facebookLogin")
    public Object facebookLogin(String accessToken) {
        //System.out.println(accessToken);
        Map<String,Object> result = new HashMap<>();
        
        try {
            URL url = new URL("https://graph.facebook.com/v3.0/me?fields=id,name,email,gender&access_token=" + accessToken);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            StringBuffer buf = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                buf.append(line);
            }
            in.close();
            
            String jsonResult = StringEscapeUtils.unescapeJson(buf.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            Map dataMap = mapper.readValue(jsonResult, Map.class);
            System.out.println(dataMap.get("id"));
            System.out.println(dataMap.get("name"));
            System.out.println(dataMap.get("email"));
            
            // DB에서 해당 이메일로 사용자를 찾아본다.
            // 있으면, 꺼내서 세션에 보관하여 로그인 처리하고,
            // 없으면, DB에 넣고 세션에 보관하여 로그인 처리한다.
            
            result.put("status", "success");
            
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }
}

//               [웹브라우저]                                  [웹서버] 
// GET 요청: /java106-java-project/auth/login ===>
//                                                       <=== 응답: 로그인폼 
// POST 요청: /java106-java-project/auth/login ===>
//                                                       <=== 응답: redirect URL
// GET 요청: /java106-java-project ===> 
//                                                       <=== 응답: index.html
// 메인화면 출력!

//ver 51 - redirect URL 변경
//ver 50 - DAO 변경에 따른 메서드 호출 변경
//ver 49 - 요청 핸들러의 파라미터 값 자동으로 주입받기
//ver 48 - CRUD 기능을 한 클래스에 합치기
//ver 47 - 애노테이션을 적용하여 요청 핸들러 다루기
//ver 46 - 페이지 컨트롤러를 POJO를 변경
//ver 45 - 프론트 컨트롤러 적용
//ver 42 - JSP 적용
//ver 41 - 클래스 추가




