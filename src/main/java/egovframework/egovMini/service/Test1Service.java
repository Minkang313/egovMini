package egovframework.egovMini.service;

import java.util.Map;

public interface Test1Service {

    Map<String, Object> getBoard1List();

    Map<String, Object> getBoard1Detail(int id);

    int addBoard(Map<String, Object> param);

    int updateboard(Map<String, Object> param);

    int deleteBoard(Map<String, Object> param);
}
