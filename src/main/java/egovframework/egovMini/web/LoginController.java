package egovframework.egovMini.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping(value = "/login.do")
    public String loginIndex(){

        return "Login/loginIndex";
    }

    @RequestMapping(value = "/sessionLogin.do")
    @ResponseBody
    public Map<String, Object> sessionLogin(@RequestBody Map<String, Object> param){

        System.out.println(param.get("username"));
        System.out.println(param.get("password"));

        return null;
    }

}
