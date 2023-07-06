package com.fisco.app.controller;

import com.fisco.app.client.PetClient;
import com.fisco.app.client.TransactionClient;
import com.fisco.app.client.UserClient;
import com.fisco.app.entity.Pet;
import com.fisco.app.entity.ResponseData;
import com.fisco.app.entity.Transaction;
import com.fisco.app.enums.UserRole;
import com.fisco.app.utils.CookieUtil;
import com.fisco.app.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TransactionController {
    
    @Autowired
    private TransactionClient transactionClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private PetClient petClient;
    @Autowired
    private TokenUtil tokenUtil;


    /**
     * 进行一次购买宠物交易
     * @param pet_id 购买的宠物pet_id
     * @return 交易成功返回 ResponseData.success("success") ; 交易失败返回 ResponseData.error("failure"); 宠物不存在返回 ResponseData.error("宠物不存在");
     */
    @PostMapping("/purchase_pet")
    public ResponseData purchase_pet(@RequestParam("pet_id") int pet_id, HttpServletRequest request) {
        //提取token
        String token = CookieUtil.getToken(request);
        String username = tokenUtil.getUsername(token);
        String role = tokenUtil.getRole(token);
        //验证身份
        if (UserRole.ROLE_USER.equals(role)) {
            //根据pet_id找到宠物的售卖人和宠物的价格
            Pet pet = petClient.query_pet_by_id(pet_id);
            //检查pet_id是否存在这个宠物
            if (pet == null) {
                //宠物不存在，交易失败
                return ResponseData.error("宠物不存在");
            } else if (pet.getHas_sold_out()) {
                //宠物已经卖出
                return ResponseData.error("宠物已售出");
            } else {
                String owner = pet.getOwner();
                int price = pet.getPrice();
                //进行转账操作
                if (userClient.transfer(username, owner, price)) {
                    //转账成功后记录交易
                    String transaction_date = LocalDate.now().toString();
                    transactionClient.record_transaction(pet_id, username, owner, transaction_date, price);
                    //并将宠物状态设为已卖出
                    petClient.set_pet_has_sold_out(pet_id);
                    return ResponseData.success("success");
                } else
                    return ResponseData.error("余额不足，转账失败");
            }
        }
        else
            return ResponseData.error("必须是普通用户才能进行购买!");
    }


    /**
     * 查询所有的交易
     * @return ResponseData.data 里包含一个交易列表，列表可能为空列表
     */
    @PostMapping("/query_all_transactions")
    public ResponseData query_all_transactions() {
        //根据pet_id找到宠物的售卖人和宠物的价格
        List<Transaction> transactionList = transactionClient.query_all_transactions();
        return ResponseData.success(transactionList);
    }

    /**
     * 通过交易购买人username查询交易记录
     * @param username g购买人
     * @return ResponseData.data 里包含一个交易记录列表
     */
    @PostMapping("/query_transactions_by_purchase_username")
    public ResponseData query_transactions_by_purchase_username(@RequestParam("username") String username) {
        List<Transaction> transaction = transactionClient.query_transactions_by_purchase_username(username);
        return ResponseData.success(transaction);
    }

    /**
     * 通过交易id查询交易记录
     * @param pet_id 交易记录
     * @return ResponseData.data 里包含一个交易记录
     */
    @PostMapping("/query_transaction_by_id")
    public ResponseData query_transaction_by_id(@RequestParam("pet_id") int pet_id) {
        Transaction transaction = transactionClient.query_transaction_by_id(pet_id);
        return ResponseData.success(transaction);
    }

    /**
     * 通过交易拥有人owner查询交易记录
     * @param owner 购买人
     * @return ResponseData.data 里包含一个交易记录列表
     */
    @PostMapping("/query_transactions_by_owner")
    public ResponseData query_transactions_by_owner(@RequestParam("owner") String owner) {
        List<Transaction> transaction = transactionClient.query_transactions_by_owner(owner);
        return ResponseData.success(transaction);
    }
    
    
}
