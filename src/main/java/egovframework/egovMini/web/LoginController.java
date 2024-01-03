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

        Map<String, Object> result = loginService.attempLogin(param, request);

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

}
