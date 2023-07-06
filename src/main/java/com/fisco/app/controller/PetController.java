package com.fisco.app.controller;

import com.fisco.app.client.PetClient;
import com.fisco.app.entity.Pet;
import com.fisco.app.entity.ResponseData;
import com.fisco.app.entity.User;
import com.fisco.app.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class PetController {

    @Autowired
    private PetClient petClient;
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/add_pet")
    public ResponseData add_pet(@RequestParam("pet_name") String pet_name,
                                @RequestParam("image_url") String image_url, @RequestParam("description") String description,
                                @RequestParam("price") int price, @RequestParam("pet_class") String pet_class,
                                HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, String> tokenMap = tokenUtil.parseToken(token);
        String username = tokenMap.get("username");
        String role = tokenMap.get("role");
        if (User.ROLE_USER.equals(role)) {
            petClient.add_pet(pet_name, username, image_url, description, price, pet_class);
            return ResponseData.success("success");
        }
        else
            return ResponseData.error("必须是普通用户才能进行购买!");

    }

    /**
     * 根据宠物id查找指定宠物
     * @param pet_id 要查找的宠物的id
     * @return 返回值ResponseData.data里包含找到的宠物，如果没有则返回ResponseData.error("can't find this pet!")
     */
    @PostMapping("/query_pet_by_id")
    public ResponseData query_pet_by_id(@RequestParam("pet_id") int pet_id) {
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
    public ResponseData query_pets_by_owner(@RequestParam("owner") String owner) {
        List<Pet> pets = petClient.query_pets_by_owner(owner);
        return ResponseData.success(pets);
    }

    /**
     * 根据宠物类别查询宠物列表
     * @param pet_class 宠物种类
     * @return 返回值里包含一个宠物列表(宠物列表可能为空)
     */
    @PostMapping("/query_pets_by_class")
    public ResponseData query_pets_by_class(@RequestParam("pet_class") String pet_class) {
        List<Pet> pets = petClient.query_pets_by_class(pet_class);
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
