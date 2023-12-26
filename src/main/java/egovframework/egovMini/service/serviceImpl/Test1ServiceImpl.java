package egovframework.egovMini.service.serviceImpl;

import egovframework.egovMini.mapper.Test1Mapper;
import egovframework.egovMini.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Test1ServiceImpl implements Test1Service {

    @Autowired Test1Mapper test1Mapper;

    @Override
    public Map<String, Object> getBoard1List() {

        Map<String, Object> board1List = new HashMap<>();
        board1List.put("board1List", test1Mapper.getBoard1List());
        return board1List;
    }
}
