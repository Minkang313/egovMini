package egovframework.egovMini.service.serviceImpl;

import egovframework.egovMini.mapper.LoginMapper;
import egovframework.egovMini.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired LoginMapper loginMapper;


    /**
     * 세션 로그인 시도
     * @param param
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> attempSessionLogin(Map<String, Object> param, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> userInfo = loginMapper.getUserInfo((String) param.get("username"));
        String loginPw = (String) param.get("password");
        String salt = (String) userInfo.get("salt");
        String pwd = (String) userInfo.get("pwd");

        try {
            if (veriSHA256Pwd(loginPw, salt, pwd)) {
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

    /**
     * salt 생성
     * @return String
     */
    private String getSalt(){

        // Random bye 객체 생성
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[20];

        // 난수 생성
        random.nextBytes(salt);

        // byte to String (10진수의 문자열로 변경)
        StringBuffer sb = new StringBuffer();
        for(byte b : salt){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * SHA256 알고리즘 - 암호화된 비밀번호 얻기
     * @param pwd
     * @param salt
     * @return String
     */
    private String getSHA256Encrypt(String pwd, String salt){
        String result = "";
        try {
            // SHA256 알고리즘 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // pwd와 salt를 합친 문자열에 SHA256 적용
            md.update((pwd+salt).getBytes());
            byte[] pwdsalt = md.digest();

            // byte to String (10진수의 문자열로 변경)
            StringBuffer sb = new StringBuffer();
            for(byte b : pwdsalt){
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * SHA-256 알고리즘의 암호화 비밀번호 로그인 성공여부 확인
     * @param loginPw
     * @param salt
     * @param pwd
     * @return
     */
    private boolean veriSHA256Pwd(String loginPw, String salt, String pwd){
        String loginEncryptPwd = getSHA256Encrypt(loginPw, salt);
        System.out.println("loginEncryptPwd: " + loginEncryptPwd);
        System.out.println("pwd: " + pwd);
        if (loginEncryptPwd.equals(pwd)) {
            return true;
        } else {
            return false;
        }
    }
}
