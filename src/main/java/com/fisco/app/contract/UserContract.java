package com.fisco.app.contract;

import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessor;


public class UserContract extends Contract {
    protected UserContract(String contractBinary, String contractAddress, Client client, CryptoKeyPair credential, TransactionProcessor transactionProcessor) {
        super(contractBinary, contractAddress, client, credential, transactionProcessor);
    }

    protected UserContract(String contractBinary, String contractAddress, Client client, CryptoKeyPair credential) {
        super(contractBinary, contractAddress, client, credential);
    }


    /**
     * 查询指定用户的余额，(不含管理员用户)
     * 如果没有这个用户，返回 -1
     * @param address 要查询的用户的地址
     * @return 余额，没有用户时返回 -1
     */
    public int query_balance(String address) {
        //需要实现
        int balance = 100;
        return balance;
    }

    /**
     * 为指定用户添加余额
     * balance += amount;
     * @param address 要添加余额的用户
     * @param amount 要添加的金额
     * @return 不存在这个用户时返回false
     */
    public boolean add_balance(String address, int amount) {
        //需要实现
        return false;
    }

    /**
     * 进行一次转账操作
     * @param from 转出账户用户地址
     * @param to 转入账户用户地址
     * @param amount 转账金额
     * @return 金额不足或账户不存在时返回false;否则转账成功返回true
     */
    public boolean transfer(String from, String to, int amount) {
        //需要实现
        return false;
    }


}
