package egovframework.egovMini.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Test2Mapper {

    List<Map<String, Object>> getBoard2List();

    Map<String, Object> getBoardDetail(int id);

    int addBoard(Map<String, Object> param);

    int updateboard(Map<String, Object> param);

    int deleteboard(int id);
}
