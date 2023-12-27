package egovframework.egovMini.web;

import egovframework.egovMini.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/test1")
public class Test1Controller {

    @Autowired Test1Service test1Service;

    @RequestMapping(value = "/board.do")
    public String board1(Model model){

        Map<String, Object> board1Map = test1Service.getBoard1List();
        model.addAttribute("board1Map", board1Map);

        return "test1/board";
    }

    /**
     * 게시판 상세조회
     * @return
     */
    @RequestMapping(value = "/boardDetail.do")
    @ResponseBody
    public Map<String, Object> boardDetail(@RequestParam(value = "id") int id){

        Map<String, Object> result = test1Service.getBoard1Detail(id);

        return result;
    }

    /**
     * 게시글 등록
     * @param param
     * @return
     */
    @RequestMapping(value = "/addBoard.do")
    @ResponseBody
    public int addBoard(@RequestParam Map<String, Object> param){

        return test1Service.addBoard(param);
    }
}
