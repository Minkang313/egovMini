package egovframework.egovMini.web;

import egovframework.egovMini.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired LoginService loginService;

    @RequestMapping(value = "/login.do")
    public String loginIndex(){

        return "Login/loginIndex";
    }

    /**
     * 세션 로그인
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value = "/sessionLogin.do")
    @ResponseBody
    public Map<String, Object> sessionLogin(@RequestBody Map<String, Object> param, HttpServletRequest request){

        Map<String, Object> result = loginService.attempSessionLogin(param, request);

        return result;
    }

    /**
     * 세션 체크 후 리턴
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkSession.do")
    @ResponseBody
    public Map<String, Object> checkSession(HttpServletRequest request){

        Map<String, Object> result = new HashMap<>();
        String loginId = (String) request.getSession().getAttribute("loginId");

        result.put("loginId", loginId);

        return result;
    }

    /**
     * 로그아웃
     * @param request
     */
    @RequestMapping(value = "/sessionLogout.do")
    @ResponseBody
    public void sessionLogout(HttpServletRequest request){
        
        request.getSession().removeAttribute("loginId");
    }

    /**
     * 쿠키 로그인
     * @param param
     * @return
     */
    @RequestMapping(value = "/cookieLogin.do")
    @ResponseBody
    public Map<String, Object> cookieLogin(@RequestBody Map<String, Object> param){

        return loginService.attemptBcryptLogin(param);
    }

    @RequestMapping(value = "/oauth/kakao.do")
    public String kakaoLogin(@RequestParam(value = "code") String code){

        // URL에 포함된 code를 이용하여 액세스 토큰 발급
        String accessToken = loginService.getKakaoAccessToken(code);
        System.out.println(accessToken);

        // 액세스 토큰을 이용하여 카카오 서버에서 유저 정보(닉네임, 이메일) 받아오기
        HashMap<String, Object> userInfo = loginService.getUserInfo(accessToken);
        System.out.println("login Controller : " + userInfo);

        return null;
    }

}


