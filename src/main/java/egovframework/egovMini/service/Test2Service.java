package egovframework.egovMini.service;

import java.util.Map;

public interface Test2Service {

    Map<String, Object> getBoard2List();

    Map<String, Object> getBoardDetail(int id);

    int addboard(Map<String, Object> param);

    int updateboard(Map<String, Object> param);

    int deleteboard(int id);
}
