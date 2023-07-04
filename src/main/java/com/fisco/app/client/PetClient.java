package com.fisco.app.client;

import com.fisco.app.common.CommonClient;
import com.fisco.app.entity.Pet;
import com.fisco.app.mapper.PetMapper;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetClient {

    @Autowired
    private PetMapper petMapper;

    public void add_pet(String pet_name, String owner, String picture_path,
                       String description, int price, String pet_class) {
        petMapper.addPet(pet_name, owner, picture_path, description, price, pet_class);
    }

    public Pet query_pet_by_id(int pet_id) {
        Pet pet = petMapper.selectById(pet_id);
        return pet;
    }

    public List<Pet> query_pets_by_owner(String owner) {
        List<Pet> pets = petMapper.selectByOwner(owner);
        return pets;
    }

    public List<Pet> query_all_pets() {
        return petMapper.selectAll();
    }

    public void set_pet_has_sold_out(int pet_id) {
        petMapper.setHasSoldOut(pet_id);
    }

}
