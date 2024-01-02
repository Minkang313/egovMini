package egovframework.egovMini.service.serviceImpl;

import egovframework.egovMini.mapper.Test2Mapper;
import egovframework.egovMini.service.Test2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Test2ServiceImpl implements Test2Service {

    @Autowired Test2Mapper test2Mapper;

    @Override
    public Map<String, Object> getBoard2List() {
        Map<String, Object> result = new HashMap<>();
        result.put("list", test2Mapper.getBoard2List());
        return result;
    }

    @Override
    public Map<String, Object> getBoardDetail(int id) {
        return test2Mapper.getBoardDetail(id);
    }

    @Override
    public int addboard(Map<String, Object> param) {
        return test2Mapper.addBoard(param);
    }

    @Override
    public int updateboard(Map<String, Object> param) {
        return test2Mapper.updateboard(param);
    }

    @Override
    public int deleteboard(int id) {
        return test2Mapper.deleteboard(id);
    }
}
