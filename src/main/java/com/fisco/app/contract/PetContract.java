package com.fisco.app.contract;

import com.fisco.app.entity.Pet;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple8;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessor;

import java.util.List;

public class PetContract extends Contract {
    protected PetContract(String contractBinary, String contractAddress, Client client, CryptoKeyPair credential, TransactionProcessor transactionProcessor) {
        super(contractBinary, contractAddress, client, credential, transactionProcessor);
    }

    protected PetContract(String contractBinary, String contractAddress, Client client, CryptoKeyPair credential) {
        super(contractBinary, contractAddress, client, credential);
    }

    /**
     * 添加一个重复，注意除了以下七个属性外，宠物还需要一个唯一的 pet_id (int类型)，可以考虑用数组下标(如果没有删除操作的话)
     * @param pet_name 宠物名
     * @param sell_username 宠物卖家的用户名
     * @param picture_path 照片路径
     * @param description 宠物描述
     * @param price 宠物的价格，浮点型double
     * @param pet_class 宠物种类
     * @param has_sold_out 是否已经卖出
     */
    public void add_pet(String pet_name, String sell_username, String picture_path,
                        String description, double price,
                        String pet_class, boolean has_sold_out) {
        //需要完成
    }

    /**
     * 根据宠物的id查询指定的一个宠物
     * @param pet_id 要查找的宠物的pet_id
     * @return 一个含8个元素的元组，顺序依照 Pet 类内成员变量顺序排列; 如果没有找到返回null
     */
    public Tuple8 query_pet_by_id(int pet_id) {
        //需要完成

        return null;
    }

    /**
     * 根据宠物的username查询指定用户的所有宠物
     * @param username 指定用户的用户名
     * @return 一个列表，列表里的每个元素都是一个 Tuple8 类型的元组，里面包含宠物的8个成员变量；如果没有找到返回空List(不是null)
     */
    public List query_pets_by_username(String username) {
        //需要完成

        return null;
    }

    /**
     * 返回所有的宠物到一个列表
     * @return 一个列表，列表里的每个元素都是一个 Tuple8 类型的元组，里面包含宠物的8个成员变量；如果没有宠物则返回空List(不是null)
     */
    public List query_all_pets() {
        //需要完成

        return null;
    }

    /**
     * 将宠物的交易状态设置已交易
     * @param pet_id 指定的宠物pet_id
     */
    public void set_pet_has_sold_out(int pet_id) {
        //需要完成
    }


}
