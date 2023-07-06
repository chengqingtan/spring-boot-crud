package com.fisco.app.client.impl;

import com.fisco.app.client.TransactionClient;
import com.fisco.app.client.UserClient;
import com.fisco.app.common.CommonClient;
import com.fisco.app.contract.TransactionContract;
import com.fisco.app.entity.Transaction;
import com.fisco.app.mapper.UserMapper;
import com.fisco.app.utils.SpringUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionClientImpl extends CommonClient implements ApplicationRunner, TransactionClient {

    @Autowired
    UserMapper userMapper;

    public void record_transaction(int pet_id, String purchase_username, String sell_username, String transaction_date, int price) {
        TransactionContract transactionContract = (TransactionContract) getContractMap().get("TransactionContract");
        transactionContract.record_transaction(BigInteger.valueOf(pet_id), purchase_username, sell_username, transaction_date, BigInteger.valueOf(price));
    }

    public List<Transaction> query_all_transactions() {
        TransactionContract transactionContract = (TransactionContract) getContractMap().get("TransactionContract");
        List<Transaction> transactionList = new ArrayList<>();
        try {
            Integer n = transactionContract.getNum().intValue();
            for(int i = 0; i < n; i++){
                BigInteger bi = BigInteger.valueOf(i);
                Tuple5<BigInteger, String, String, String, BigInteger> t = transactionContract.query_all_transaction(bi);
                transactionList.add(new Transaction(t.getValue1().intValue(), t.getValue2(), t.getValue3(), t.getValue4(), t.getValue5().intValue()));
            }

        } catch (ContractException e) {
            System.err.println(getClass().getName() + " : query_all_transactions 抛出异常");
            throw new RuntimeException(e);
        }
        return transactionList;
    }

    public Transaction query_transaction_by_id(int pet_id) {
        TransactionContract transactionContract = (TransactionContract) getContractMap().get("TransactionContract");
        try {
            Integer n = transactionContract.getNum().intValue();
            for(int i = 0; i < n; i++) {
                BigInteger bi = BigInteger.valueOf(i);
                Tuple5<BigInteger, String, String, String, BigInteger> t = transactionContract.query_all_transaction(bi);
                if (t.getValue1().intValue() == pet_id) {
                    Transaction transaction = new Transaction(t.getValue1().intValue(), t.getValue2(), t.getValue3(), t.getValue4(), t.getValue5().intValue());
                    return transaction;
                }
            }
            return null;
        } catch (ContractException e) {
            System.err.println(getClass().getName() + " : query_all_transactions 抛出异常");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> query_transactions_by_purchase_username(String username) {
        //通过owner查询address
        String address = userMapper.selectByUsername(username).getAddress();
        TransactionContract transactionContract = (TransactionContract) getContractMap().get("TransactionContract");
        List<Transaction> transactionList = new ArrayList<>();
        try {
            Integer n = transactionContract.getNum().intValue();
            for(int i = 0; i < n; i++) {
                BigInteger bi = BigInteger.valueOf(i);
                Tuple5<BigInteger, String, String, String, BigInteger> t = transactionContract.query_all_transaction(bi);
                if (t.getValue2().equals(username)) {
                    transactionList.add(new Transaction(t.getValue1().intValue(), t.getValue2(), t.getValue3(), t.getValue4(), t.getValue5().intValue()));

                }
            }
            return transactionList;
        } catch (ContractException e) {
            System.err.println(getClass().getName() + " : query_all_transactions 抛出异常");
            throw new RuntimeException(e);
        }

    }
    public List<Transaction> query_transactions_by_owner(String owner) {
        //通过owner查询address
        String address = userMapper.selectByUsername(owner).getAddress();
        TransactionContract transactionContract = (TransactionContract) getContractMap().get("TransactionContract");
        List<Transaction> transactionList = new ArrayList<>();
        try {
            Integer n = transactionContract.getNum().intValue();
            for(int i = 0; i < n; i++) {
                BigInteger bi = BigInteger.valueOf(i);
                Tuple5<BigInteger, String, String, String, BigInteger> t = transactionContract.query_all_transaction(bi);
                System.out.println(t.getValue3());
                if (t.getValue3().equals(owner)) {
                    System.out.println(t.getValue1());
                    transactionList.add(new Transaction(t.getValue1().intValue(), t.getValue2(), t.getValue3(), t.getValue4(), t.getValue5().intValue()));
                }
            }
            return transactionList;
        } catch (ContractException e) {
            System.err.println(getClass().getName() + " : query_all_transactions 抛出异常");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BcosSDK sdk = SpringUtils.getBean("bcosSDK");
        deploy("TransactionContract", TransactionContract.class, sdk);
    }
}
