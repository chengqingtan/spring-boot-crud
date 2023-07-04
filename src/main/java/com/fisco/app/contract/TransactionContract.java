package com.fisco.app.contract;

import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessor;

public class TransactionContract extends Contract {
    protected TransactionContract(String contractBinary, String contractAddress, Client client, CryptoKeyPair credential, TransactionProcessor transactionProcessor) {
        super(contractBinary, contractAddress, client, credential, transactionProcessor);
    }

    protected TransactionContract(String contractBinary, String contractAddress, Client client, CryptoKeyPair credential) {
        super(contractBinary, contractAddress, client, credential);
    }


    /**
     * 存储一次交易记录，除了以下参数还要生成一个独一无二的 transaction_id 存储起来
     * @param pet_id 交易的宠物
     * @param purchase_username 购买用户名
     * @param owner 出售宠物的用户名
     * @param transaction_date 交易日期
     * @param price 交易价格
     */
    public void record_transaction(int pet_id, String purchase_username, String owner, String transaction_date, int price) {
        //需要完成

    }

}
