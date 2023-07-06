package com.fisco.app.client.impl;

import com.fisco.app.client.PetClient;
import com.fisco.app.entity.Pet;
import com.fisco.app.mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetClientImpl implements PetClient {

    @Autowired
    private PetMapper petMapper;

    public void add_pet(String pet_name, String owner, String image_url,
                        String description, int price, String pet_class) {
        petMapper.addPet(pet_name, owner, image_url, description, price, pet_class);
    }

    public Pet query_pet_by_id(int pet_id) {
        return petMapper.selectById(pet_id);
    }

    public List<Pet> query_pets_by_owner(String owner) {
        return petMapper.selectByOwner(owner);
    }

    public List<Pet> query_all_pets() {
        return petMapper.selectAll();
    }

    public List<Pet> query_pets_by_class(String pet_class) {
        return petMapper.selectByClass(pet_class);
    }

    public void set_pet_has_sold_out(int pet_id) {
        petMapper.setHasSoldOut(pet_id);
    }

}