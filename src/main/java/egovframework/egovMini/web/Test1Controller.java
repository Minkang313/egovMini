package egovframework.egovMini.web;

import egovframework.egovMini.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = "/test1")
public class Test1Controller {

    @Autowired Test1Service test1Service;

    @RequestMapping(value = "/board.do")
    public String board1(){

        Map<String, Object> board1Map = test1Service.getBoard1List();
        System.out.println("board1Map" + board1Map);

        return "test1/board";
    }

}
