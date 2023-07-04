package com.fisco.app.client;

import com.fisco.app.contract.TransactionContract;
import com.fisco.app.entity.Transaction;

import java.util.List;

public interface TransactionClient {

    void record_transaction(int pet_id, String purchase_username, String sell_username, String transaction_date, int price);

    List<Transaction> query_all_transactions();

    public Transaction query_transaction_by_id(int transaction_id);

    /**
     * 未完成，无法处理的返回值类型
     */
    List<Transaction> query_transaction_by_owner(String owner);
}
