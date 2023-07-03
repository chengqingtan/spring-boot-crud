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
    public ResponseData add_pet(@RequestBody String pet_name, @RequestBody String sell_username,
                                @RequestBody String picture_path, @RequestBody String description,
                                @RequestBody double price, @RequestBody String pet_class,
                                @RequestBody boolean has_sold_out) {
        petClient.add_pet(pet_name, sell_username, picture_path, description, price, pet_class, has_sold_out);
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
     * @param username 指定的用户名
     * @return 返回值里包含一个宠物列表(宠物列表可能为空)
     */
    @PostMapping("/query_pets_by_username")
    public ResponseData query_pets_by_username(@RequestBody String username) {
        List<Pet> pets = petClient.query_pets_by_username(username);
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
