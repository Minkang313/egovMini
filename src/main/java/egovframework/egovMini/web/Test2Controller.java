package egovframework.egovMini.web;

import egovframework.egovMini.service.Test2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/test2")
public class Test2Controller {

    @Autowired Test2Service test2Service;

    @RequestMapping(value = "/board.do")
    public String board2(@RequestParam(value = "curPage", defaultValue = "1", required = false) int curPage, Model model){

        Map<String, Object> result = test2Service.getBoard2List();
        model.addAttribute("result", result);

        return "test2/board";
    }

    @RequestMapping(value = "/getBoardDetail.do")
    @ResponseBody
    public Map<String, Object> getBoardDetail(@RequestParam(value = "id") int id){

        return test2Service.getBoardDetail(id);
    }

    @RequestMapping(value = "/addboard.do")
    @ResponseBody
    public int addBoard(@RequestParam Map<String, Object> param){
        return test2Service.addboard(param);
    }

    @RequestMapping(value = "/updateBoard.do")
    @ResponseBody
    public int updateboard(@RequestParam Map<String, Object> param){
        return test2Service.updateboard(param);
    }

    @RequestMapping(value = "/deleteBoard.do")
    @ResponseBody
    public int deleteBoard(@RequestParam(value = "id") int id){
        return test2Service.deleteboard(id);
    }
}
