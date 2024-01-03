package egovframework.egovMini.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {

    Map<String, Object> attempLogin(Map<String, Object> param, HttpServletRequest request);
}
