package egovframework.egovMini.service.serviceImpl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import egovframework.egovMini.mapper.LoginMapper;
import egovframework.egovMini.service.LoginService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
     * BCrypt 로그인
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> attemptBcryptLogin(Map<String, Object> param) {

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> userInfo = loginMapper.getUserInfo((String) param.get("username"));
        String loginPw = (String) param.get("password");
        String bcrypt = (String) userInfo.get("bcrypt");
        if (veriBCryptPwd(loginPw, bcrypt)) {
            result.put("resultCode", "S");
            result.put("msg", "로그인에 성공하였습니다.");
            result.put("loginId", param.get("username"));
        } else {
            result.put("resultCode", "F");
            result.put("msg", "로그인에 실패하였습니다.");
        }

        return result;
    }

    /**
     * Access Token 얻기
     * @param code
     * @return
     */
    @Override
    public String getKakaoAccessToken (String code) {
        String accessToken = "";
        String refreshToken = "";
        String requestURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            // setDoOutput()은 OutputStream으로 POST 데이터를 넘겨 주겠다는 옵션이다.
            // POST 요청을 수행하려면 setDoOutput()을 true로 설정한다.
            conn.setDoOutput(true);

            // POST 요청에서 필요한 파라미터를 OutputStream을 통해 전송
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=ea03b7cd1fb602deba0647ceb4669bc2" + // REST_API_KEY
                    "&redirect_uri=http://localhost:8080/oauth/kakao.do" + // REDIRECT_URI
                    "&code=" + code;
            bufferedWriter.write(sb);
            bufferedWriter.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 데이터를 InputStreamReader을 통해 읽어 오기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("accessToken : " + accessToken);
            System.out.println("refreshToken : " + refreshToken);

            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    /**
     * 카카오 로그인 정보 얻기
     * @param accessToken
     * @return
     */
    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String postURL = "https://kapi.kakao.com/v2/user/me";
        System.out.println("로그인 정보 얻기");

        try {
            URL url = new URL(postURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
//            userInfo.put("email", email);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return userInfo;
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
        return loginEncryptPwd.equals(pwd);
    }

    /**
     * BCrypt 비밀번호 해싱
     * @param password
     * @return
     */
     public String getBCryptPwd(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * BCrypt 라이브러리의 비밀번호 일치 여부 확인
     * @param loginPw
     * @param pwd
     * @return
     */
    public boolean veriBCryptPwd(String loginPw, String pwd){
        return BCrypt.checkpw(loginPw, pwd);
    }


}

