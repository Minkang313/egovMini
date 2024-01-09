package egovframework.egovMini.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface LoginService {

    Map<String, Object> attempSessionLogin(Map<String, Object> param, HttpServletRequest request);

    Map<String, Object> attemptBcryptLogin(Map<String, Object> param);

    String getKakaoAccessToken (String code);

    HashMap<String, Object> getUserInfo(String accessToken);
}
