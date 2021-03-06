package com.cping.shirospringboot.mapper;

import com.cping.shirospringboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    public User queryUserByName(String username);
}
