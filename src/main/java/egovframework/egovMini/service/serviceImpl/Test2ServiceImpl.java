package egovframework.egovMini.service.serviceImpl;

import egovframework.egovMini.mapper.Test2Mapper;
import egovframework.egovMini.service.Test2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Test2ServiceImpl implements Test2Service {

    @Autowired Test2Mapper test2Mapper;
}
