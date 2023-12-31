package com.fisco.app.mapper;


import com.fisco.app.entity.Pet;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PetMapper {

    List<Pet> selectAll();

    Pet selectById(@Param("pet_id") int pet_id);

    List<Pet> selectByOwner(@Param("owner") String owner);

    List<Pet> selectByClass(@Param("pet_class") String pet_class);

    @Insert("insert into tb_pet (pet_name, owner, image_url, description, price, pet_class) " +
            "VALUES (#{pet_name},#{owner},#{image_url},#{description},#{price},#{pet_class});")
    void addPet(@Param("pet_name") String pet_name, @Param("owner") String owner, @Param("image_url") String image_url,
                @Param("description") String description, @Param("price") int price, @Param("pet_class") String pet_class);

    @Update("update tb_pet set pet_status='sold_out' where pet_id=#{pet_id};")
    void setHasSoldOut(@Param("pet_id") int pet_id);

}
