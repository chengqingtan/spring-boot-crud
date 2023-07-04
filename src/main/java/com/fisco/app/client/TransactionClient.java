package com.fisco.app.client;

import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.TransactionContract;
import com.fisco.app.contract.UserContract;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class TransactionClient extends CommonClient implements ApplicationRunner {

    public static final Logger logger = LoggerFactory.getLogger(TransactionClient.class.getName());

    public void record_transaction(int pet_id, String purchase_username, String sell_username, String transaction_date, int price) {
        TransactionContract transactionContract = (TransactionContract) getContractMap().get("TransactionContract");
        transactionContract.record_transaction(pet_id, purchase_username, sell_username, transaction_date, price);
        logger.info("调用TransactionClient的record_transaction方法");
        logger.info("结果：{}", "void");
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        BcosSDK sdk = SpringUtils.getBean("bcosSDK");
        deploy("TransactionContract", TransactionContract.class, sdk);
    }
}
