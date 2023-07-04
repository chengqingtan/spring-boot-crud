package com.fisco.app.client;

import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.UserContract;
import com.fisco.app.entity.Pet;
import com.fisco.app.entity.User;
import com.fisco.app.mapper.UserMapper;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;

@Service
public class UserClient extends CommonClient implements ApplicationRunner  {

    public static final Logger logger = LoggerFactory.getLogger(UserClient.class.getName());

    @Autowired
    private UserMapper userMapper;

    public boolean user_login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user==null || !password.equals(user.getPassword()))
            return false;
        else
            return true;
    }

    public boolean admin_login(String username, String password) {
        //未完成
        if("admin".equals(username) && "password".equals(password))
            return true;
        else
            return false;
    }

    public boolean register(String username, String password) {
        //首先检查username是否重复
        User user = userMapper.selectByUsername(username);
        if(user!=null)
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
            userMapper.addUser(new User(username,password,privateKey,publicKey,address));
            return true;
        }
    }

    public double query_balance(String address) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        double balance = userContract.query_balance(address);
        logger.info("调用UserClient的query_balance方法");
        logger.info("结果：{}", balance);
        return balance;
    }

    public boolean add_balance(String address, int amount) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_add_success = userContract.add_balance(address, amount);
        logger.info("调用UserClient的add_balance方法");
        logger.info("结果：{}", whether_add_success);
        return whether_add_success;
    }

    public boolean transfer(String from, String to, int amount) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_transfer_success = userContract.transfer(from, to, amount);
        logger.info("调用UserClient的transfer方法");
        logger.info("结果：{}", whether_transfer_success);
        return whether_transfer_success;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BcosSDK sdk = SpringUtils.getBean("bcosSDK");
        deploy("UserContract", UserContract.class, sdk);
    }
}
