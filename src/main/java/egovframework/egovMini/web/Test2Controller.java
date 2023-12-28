package egovframework.egovMini.web;

import egovframework.egovMini.service.Test2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Test2Controller {

    @Autowired Test2Service test2Service;
}
