package com.fisco.app.client;

import com.fisco.app.contract.TransactionContract;
import com.fisco.app.entity.Transaction;

import java.util.List;

public interface TransactionClient {

    void record_transaction(int pet_id, String purchase_username, String sell_username, String transaction_date, int price);

    List<Transaction> query_all_transactions();

    Transaction query_transaction_by_id(int transaction_id);

    List<Transaction> query_transactions_by_purchase_username(String username);

    List<Transaction> query_transactions_by_owner(String owner);
}
