package com.fisco.app.controller;

import com.fisco.app.client.PetClient;
import com.fisco.app.entity.Pet;
import com.fisco.app.entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetClient petClient;

    @PostMapping("/add_pet")
    public ResponseData add_pet(@RequestBody String pet_name, @RequestBody String owner,
                                @RequestBody String picture_path, @RequestBody String description,
                                @RequestBody int price, @RequestBody String pet_class) {
        petClient.add_pet(pet_name, owner, picture_path, description, price, pet_class);
        return ResponseData.success("success");
    }

    /**
     * 根据宠物id查找指定宠物
     * @param pet_id 要查找的宠物的id
     * @return 返回值ResponseData.data里包含找到的宠物，如果没有则返回ResponseData.error("can't find this pet!")
     */
    @PostMapping("/query_pet_by_id")
    public ResponseData query_pet_by_id(@RequestBody int pet_id) {
        Pet pet = petClient.query_pet_by_id(pet_id);
        if(pet == null)
            return ResponseData.error("can't find this pet!");
        else
            return ResponseData.success(pet);
    }

    /**
     * 根据用户名查找这个用户售卖的所有宠物
     * @param owner 指定的宠物所有者的用户名
     * @return 返回值里包含一个宠物列表(宠物列表可能为空)
     */
    @PostMapping("/query_pets_by_owner")
    public ResponseData query_pets_by_owner(@RequestBody String owner) {
        List<Pet> pets = petClient.query_pets_by_owner(owner);
        return ResponseData.success(pets);
    }

    /**
     * 查找所有宠物
     * @return 返回值里包含一个宠物列表(宠物列表可能为空)
     */
    @PostMapping("/query_all_pets")
    public ResponseData query_all_pets() {
        List<Pet> pets = petClient.query_all_pets();
        return ResponseData.success(pets);
    }


}
