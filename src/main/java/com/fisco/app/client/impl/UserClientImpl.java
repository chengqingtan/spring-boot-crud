package com.fisco.app.client.impl;

import com.fisco.app.client.UserClient;
import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.UserContract;
import com.fisco.app.entity.User;
import com.fisco.app.enums.UserRole;
import com.fisco.app.mapper.UserMapper;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;

import java.math.BigInteger;

@Service
public class UserClientImpl extends CommonClient implements ApplicationRunner, UserClient {

    @Autowired
    private UserMapper userMapper;

    public boolean login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null || !password.equals(user.getPassword()))
            return false;
        else
            return true;
    }

    public String query_role(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null)
            return null;
        else
            return user.getRole();
    }

    public boolean register(String username, String password) {
        //首先检查username是否重复
        User user = userMapper.selectByUsername(username);
        if (user != null)
            return false;
        else {
            //创建普通账户
            EncryptType.encryptType = 0;
            Credentials credentials = GenCredential.create();
            //账户地址
            String address = credentials.getAddress();
            //账户私钥
            String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
            //账户公钥
            String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
            //存入到数据库
            userMapper.addUser(new User(username, password, privateKey, publicKey, address, UserRole.ROLE_USER));
            //添加用户到合约
            UserContract userContract = (UserContract) getContractMap().get("UserContract");
            userContract.add_user(address);
            return true;
        }
    }

    public int query_balance(String username) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        int balance = 0;
        try {
            String address = userMapper.selectByUsername(username).getAddress();
            balance = userContract.query_balance(address).intValue();
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }

    public boolean add_balance(String username, int amount) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        User user = userMapper.selectByUsername(username);
        if(user == null)
            return false;
        else {
            String address = user.getAddress();
            userContract.add_balance(address, BigInteger.valueOf(amount));
            return true;
        }
    }

    public boolean transfer(String from, String to, int amount) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        String from_address = userMapper.selectByUsername(from).getAddress();
        String to_address = userMapper.selectByUsername(to).getAddress();

        TransactionReceipt receipt = userContract.transfer(from_address, to_address, BigInteger.valueOf(amount));
        if (receipt.isStatusOK())
            return true;
        else
            return false;
    }

    public String query_address_by_username(String username) {
        return userMapper.selectByUsername(username).getAddress();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BcosSDK sdk = SpringUtils.getBean("bcosSDK");
        deploy("UserContract", UserContract.class, sdk);
    }
}

