package egovframework.egovMini.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.Map;

@Mapper
public interface LoginMapper {

    int attempLogin(Map<String, Object> param);

    Map<String, Object> getUserInfo(String username);
}
