package com.fisco.app.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class UserContract extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610418806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630657a947146100515780632d581e711461008d57806330dc9cee1461010b578063beabacc81461011e575b600080fd5b61007a61005f3660046102eb565b6001600160a01b031660009081526001602052604090205490565b6040519081526020015b60405180910390f35b6100fb61009b3660046102eb565b6002805460018181019092557f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0180546001600160a01b039093166001600160a01b03199093168317905560009182526020819052604082209190915590565b6040519015158152602001610084565b6100fb610119366004610306565b610131565b6100fb61012c366004610330565b6101e2565b60006001600160a01b038316156101d85760025460005b818110156101cd57600281815481106101635761016361036c565b6000918252602090912001546001600160a01b03868116911614156101bb576001600160a01b038516600090815260016020526040812080548692906101aa908490610398565b90915550600193506101dc92505050565b806101c5816103b0565b915050610148565b5060009150506101dc565b5060005b92915050565b6001600160a01b03831660009081526001602052604081205482111561020a575060006102c8565b60025460005b818110156102c2576002818154811061022b5761022b61036c565b6000918252602090912001546001600160a01b03868116911614156102b0576001600160a01b038616600090815260016020526040812080548692906102729084906103cb565b90915550506001600160a01b0385166000908152600160205260408120805486929061029f908490610398565b90915550600193506102c892505050565b806102ba816103b0565b915050610210565b50505060005b9392505050565b80356001600160a01b03811681146102e657600080fd5b919050565b6000602082840312156102fd57600080fd5b6102c8826102cf565b6000806040838503121561031957600080fd5b610322836102cf565b946020939093013593505050565b60008060006060848603121561034557600080fd5b61034e846102cf565b925061035c602085016102cf565b9150604084013590509250925092565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b600082198211156103ab576103ab610382565b500190565b60006000198214156103c4576103c4610382565b5060010190565b6000828210156103dd576103dd610382565b50039056fea2646970667358221220a64c74b183f61a0651c7182106c4f4f4b6b733b9635be2e5f3a5ecdaef0301aa64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610418806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80633168a4d0146100515780639e7225c4146100d4578063d20ec0d51461010b578063e308a8861461011e575b600080fd5b6100bf61005f3660046102eb565b6002805460018181019092557fd35f1a30cf220fbb02dc852aaf21cae20338ac7d10d548ea52adafe8261a78730180546001600160a01b039093166001600160a01b03199093168317905560009182526020819052604082209190915590565b60405190151581526020015b60405180910390f35b6100fd6100e23660046102eb565b6001600160a01b031660009081526001602052604090205490565b6040519081526020016100cb565b6100bf610119366004610306565b610131565b6100bf61012c366004610330565b6101e2565b60006001600160a01b038316156101d85760025460005b818110156101cd57600281815481106101635761016361036c565b6000918252602090912001546001600160a01b03868116911614156101bb576001600160a01b038516600090815260016020526040812080548692906101aa908490610398565b90915550600193506101dc92505050565b806101c5816103b0565b915050610148565b5060009150506101dc565b5060005b92915050565b6001600160a01b03831660009081526001602052604081205482111561020a575060006102c8565b60025460005b818110156102c2576002818154811061022b5761022b61036c565b6000918252602090912001546001600160a01b03868116911614156102b0576001600160a01b038616600090815260016020526040812080548692906102729084906103cb565b90915550506001600160a01b0385166000908152600160205260408120805486929061029f908490610398565b90915550600193506102c892505050565b806102ba816103b0565b915050610210565b50505060005b9392505050565b80356001600160a01b03811681146102e657600080fd5b919050565b6000602082840312156102fd57600080fd5b6102c8826102cf565b6000806040838503121561031957600080fd5b610322836102cf565b946020939093013593505050565b60008060006060848603121561034557600080fd5b61034e846102cf565b925061035c602085016102cf565b9150604084013590509250925092565b63b95aa35560e01b600052603260045260246000fd5b63b95aa35560e01b600052601160045260246000fd5b600082198211156103ab576103ab610382565b500190565b60006000198214156103c4576103c4610382565b5060010190565b6000828210156103dd576103dd610382565b50039056fea2646970667358221220e69b19fc7c9d1bee6d6d7de11af2275530b9709d6fb231977857d0bf7724aee764736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"add_balance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[819764462,3524182229],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"add_user\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[760749681,828941520],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"query_balance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[106408263,2658280900],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[3198921928,3808995462],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ADD_BALANCE = "add_balance";

    public static final String FUNC_ADD_USER = "add_user";

    public static final String FUNC_QUERY_BALANCE = "query_balance";

    public static final String FUNC_TRANSFER = "transfer";

    protected UserContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public TransactionReceipt add_balance(String account, BigInteger amount) {
        final Function function = new Function(
                FUNC_ADD_BALANCE, 
                Arrays.<Type>asList(new Address(account),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForAdd_balance(String account, BigInteger amount) {
        final Function function = new Function(
                FUNC_ADD_BALANCE, 
                Arrays.<Type>asList(new Address(account),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String add_balance(String account, BigInteger amount, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADD_BALANCE, 
                Arrays.<Type>asList(new Address(account),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, BigInteger> getAdd_balanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADD_BALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public Tuple1<Boolean> getAdd_balanceOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ADD_BALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public TransactionReceipt add_user(String account) {
        final Function function = new Function(
                FUNC_ADD_USER, 
                Arrays.<Type>asList(new Address(account)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForAdd_user(String account) {
        final Function function = new Function(
                FUNC_ADD_USER, 
                Arrays.<Type>asList(new Address(account)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String add_user(String account, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADD_USER, 
                Arrays.<Type>asList(new Address(account)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<String> getAdd_userInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADD_USER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<Boolean> getAdd_userOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ADD_USER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public BigInteger query_balance(String account) throws ContractException {
        final Function function = new Function(FUNC_QUERY_BALANCE, 
                Arrays.<Type>asList(new Address(account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt transfer(String from, String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(from),
                new Address(to),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForTransfer(String from, String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(from),
                new Address(to),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String transfer(String from, String to, BigInteger amount,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(from),
                new Address(to),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple3<String, String, BigInteger> getTransferInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public Tuple1<Boolean> getTransferOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_TRANSFER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public static UserContract load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new UserContract(contractAddress, client, credential);
    }

    public static UserContract deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(UserContract.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
