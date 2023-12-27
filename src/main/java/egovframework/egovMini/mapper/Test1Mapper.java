package egovframework.egovMini.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Test1Mapper {

    public List<Map<String, Object>> getBoard1List();

    public Map<String, Object> getBoard1Detail(int id);

    int addBoard(Map<String, Object> param);
}
