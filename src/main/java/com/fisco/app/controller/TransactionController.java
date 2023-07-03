package com.fisco.app.controller;

import com.fisco.app.client.PetClient;
import com.fisco.app.client.TransactionClient;
import com.fisco.app.client.UserClient;
import com.fisco.app.entity.Pet;
import com.fisco.app.entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class TransactionController {
    
    @Autowired
    private TransactionClient transactionClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private PetClient petClient;


    /**
     * 进行一次购买宠物交易
     * @param username 购买者的用户名
     * @param pet_id 购买的宠物pet_id
     * @return 交易成功返回 ResponseData.success("success") ; 交易失败返回 ResponseData.error("failure")
     */
    @PostMapping("/purchase_pet")
    public ResponseData purchase_pet(@RequestBody String username, @RequestBody int pet_id) {
        //根据pet_id找到宠物的售卖人和宠物的价格
        Pet pet = petClient.query_pet_by_id(pet_id);
        String sellUsername = pet.getSell_username();
        double price = pet.getPrice();
        //进行转账操作
        if (userClient.transfer(username, sellUsername, price)) {
            //转账成功后记录交易
            String transaction_date = LocalDate.now().toString();
            transactionClient.record_transaction(pet_id, username, sellUsername, transaction_date);
            //并将宠物状态设为已卖出
            petClient.set_pet_has_sold_out(pet_id);
            return ResponseData.success("success");
        }
        else
            return ResponseData.error("failure");
    }
    
    
    
}
