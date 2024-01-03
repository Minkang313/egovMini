package egovframework.egovMini.service.serviceImpl;

import egovframework.egovMini.mapper.LoginMapper;
import egovframework.egovMini.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired LoginMapper loginMapper;


    @Override
    public Map<String, Object> attempLogin(Map<String, Object> param, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        try {
            int checkIdPwd = loginMapper.attempLogin(param);

            if (1 == checkIdPwd) {
                result.put("resultCode", "S");
                result.put("msg", "로그인에 성공하였습니다.");

                request.getSession().setAttribute("loginId", param.get("username"));
            } else {
                result.put("resultCode", "F");
                result.put("msg", "로그인에 실패하였습니다.");
            }
        } catch (RuntimeException e) {
            result.put("resultCode", "E");
            result.put("msg", "로그인 에러");
        }

        return result;
    }
}
