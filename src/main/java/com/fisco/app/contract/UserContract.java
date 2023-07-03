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
     * 普通用户登录
     * @param username 用户名
     * @param password 密码
     * @return 是否登陆成功，如果没有这个用户或密码错误，返回false
     */
    public boolean user_login(String username, String password) {
        //需要实现
        return false;
    }

    /**
     * 管理员用户登录
     * @param username 用户名
     * @param password 密码
     * @return 是否登陆成功，如果没有这个用户或密码错误，返回false
     */
    public boolean admin_login(String username, String password) {
        //需要实现
        return false;
    }

    /**
     * 注册新用户，要求 username 唯一
     * 注册的是普通用户，不能注册管理员，管理员只有一个初始管理员
     * 用户初始余额为 0.0
     * @param username 用户名
     * @param password 密码
     * @return 如果用户名重复，注册失败，返回false；否则注册成功返回true
     */
    public boolean register(String username, String password) {
        //需要实现
        return false;
    }


    /**
     * 查询指定用户的余额，(不含管理员用户)
     * 余额为浮点型，如果没有这个用户，返回 -1.0
     * @param username 要查询的用户
     * @return 余额，没有用户时返回 -1.0
     */
    public double query_balance(String username) {
        //需要实现
        double balance = 100;
        return balance;
    }

    /**
     * 为指定用户添加余额
     * 余额为浮点型
     * balance += amount;
     * @param username 要添加余额的用户
     * @param amount 要添加的金额
     * @return 不存在这个用户时返回false
     */
    public boolean add_balance(String username, double amount) {
        //需要实现
        return false;
    }

    /**
     * 进行一次转账操作
     * @param from_username 转出账户用户名
     * @param to_username 转入账户用户名
     * @param amount 转账金额
     * @return 金额不足或账户不存在时返回false;否则转账成功返回true
     */
    public boolean transfer(String from_username, String to_username, double amount) {
        //需要实现
        return false;
    }


}
