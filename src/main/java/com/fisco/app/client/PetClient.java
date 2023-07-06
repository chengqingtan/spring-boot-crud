package com.fisco.app.client;

import com.fisco.app.entity.Pet;
import java.util.List;

public interface PetClient {

    void add_pet(String pet_name, String owner, String picture_path,
                       String description, int price, String pet_class);

    Pet query_pet_by_id(int pet_id);

    List<Pet> query_pets_by_owner(String owner);

    List<Pet> query_all_pets();

    List<Pet> query_pets_by_class(String pet_class);

    void set_pet_has_sold_out(int pet_id);

}
