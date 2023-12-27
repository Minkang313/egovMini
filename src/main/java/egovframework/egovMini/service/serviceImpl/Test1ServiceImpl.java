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

    /**
     * 게시판 상세조회
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getBoard1Detail(int id) {
        return test1Mapper.getBoard1Detail(id);
    }


    /**
     * 게시글 등록
     * @param param
     * @return
     */
    @Override
    public int addBoard(Map<String, Object> param) {
        return test1Mapper.addBoard(param);
    }

    /**
     * 게시글 수정
     * @param param
     * @return
     */
    @Override
    public int updateboard(Map<String, Object> param) {
        return test1Mapper.updateboard(param);
    }

    /**
     * 게시글 삭제
     * @param param
     * @return
     */
    @Override
    public int deleteBoard(Map<String, Object> param) {
        return test1Mapper.deleteBoard(param);
    }
}
