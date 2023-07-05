package com.fisco.app.mapper;


import com.fisco.app.entity.Pet;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PetMapper {

//    @Select("select * from tb_pet;")
    List<Pet> selectAll();

//    @Select("select * from tb_pet where pet_id=#{pet_id};")
    Pet selectById(@Param("pet_id") int pet_id);


//    @Select("select * from tb_pet where owner=#{owner};")
    List<Pet> selectByOwner(@Param("owner") String owner);

    @Insert("insert into tb_pet (pet_name, owner, image_url, description, price, pet_class) " +
            "VALUES (#{pet_name},#{owner},#{image_url},#{description},#{price},#{pet_class});")
    void addPet(@Param("pet_name") String pet_name, @Param("owner") String owner, @Param("image_url") String image_url,
                @Param("description") String description, @Param("price") int price, @Param("pet_class") String pet_class);

    @Update("update tb_pet set has_sold_out=true where pet_id=#{pet_id};")
    void setHasSoldOut(@Param("pet_id") int pet_id);

}
