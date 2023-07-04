package com.fisco.app.mapper;


import com.fisco.app.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from tb_user where username=#{username}")
    User selectByUsername(@Param("username") String username);

    @Insert("insert into tb_user (username, password, private_key, public_key, address) " +
            "VALUES (#{username}, #{password}, #{private_key}, #{public_key}, #{address});")
    void addUser(User user);

}
