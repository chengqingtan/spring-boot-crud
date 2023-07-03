package com.fisco.app.client;

import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.UserContract;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class UserClient extends CommonClient implements ApplicationRunner  {

    public static final Logger logger = LoggerFactory.getLogger(UserClient.class.getName());

    public boolean user_login(String username, String password) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_login_success = userContract.user_login(username, password);
        logger.info("调用UserClient的user_login方法");
        logger.info("结果：{}", whether_login_success);
        return whether_login_success;
    }

    public boolean admin_login(String username, String password) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_login_success = userContract.admin_login(username, password);
        logger.info("调用UserClient的admin_login方法");
        logger.info("结果：{}", whether_login_success);
        return whether_login_success;
    }

    public boolean register(String username, String password) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_register_success = userContract.register(username, password);
        logger.info("调用UserClient的register方法");
        logger.info("结果：{}", whether_register_success);
        return whether_register_success;
    }

    public double query_balance(String username) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        double balance = userContract.query_balance(username);
        logger.info("调用UserClient的query_balance方法");
        logger.info("结果：{}", balance);
        return balance;
    }

    public boolean add_balance(String username, double amount) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_add_success = userContract.add_balance(username, amount);
        logger.info("调用UserClient的add_balance方法");
        logger.info("结果：{}", whether_add_success);
        return whether_add_success;
    }

    public boolean transfer(String from_username, String to_username, double amount) {
        UserContract userContract = (UserContract) getContractMap().get("UserContract");
        boolean whether_transfer_success = userContract.transfer(from_username, to_username, amount);
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
