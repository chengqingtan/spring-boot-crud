package com.fisco.app.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
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
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b0319163317905561041f806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630657a947146100515780632d581e711461007657806330dc9cee146100e0578063beabacc8146100f3575b600080fd5b61006461005f3660046102eb565b610106565b60405190815260200160405180910390f35b6100de6100843660046102eb565b6002805460018181019092557f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0180546001600160a01b039093166001600160a01b03199093168317905560009182526020526040812055565b005b6100de6100ee36600461030d565b610185565b6100de610101366004610337565b61021b565b600254600090815b81811015610178576002818154811061012957610129610373565b6000918252602090912001546001600160a01b0385811691161415610166575050506001600160a01b031660009081526001602052604090205490565b806101708161039f565b91505061010e565b50633b9ac9ff9392505050565b6001600160a01b038216156102175760025460005b8181101561021457600281815481106101b5576101b5610373565b6000918252602090912001546001600160a01b0385811691161415610202576001600160a01b038416600090815260016020526040812080548592906101fc9084906103ba565b90915550505b8061020c8161039f565b91505061019a565b50505b5050565b60025460005b818110156102c8576002818154811061023c5761023c610373565b6000918252602090912001546001600160a01b03858116911614156102b6576001600160a01b038516600090815260016020526040812080548592906102839084906103d2565b90915550506001600160a01b038416600090815260016020526040812080548592906102b09084906103ba565b90915550505b806102c08161039f565b915050610221565b5050505050565b80356001600160a01b03811681146102e657600080fd5b919050565b6000602082840312156102fd57600080fd5b610306826102cf565b9392505050565b6000806040838503121561032057600080fd5b610329836102cf565b946020939093013593505050565b60008060006060848603121561034c57600080fd5b610355846102cf565b9250610363602085016102cf565b9150604084013590509250925092565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60006000198214156103b3576103b3610389565b5060010190565b600082198211156103cd576103cd610389565b500190565b6000828210156103e4576103e4610389565b50039056fea26469706673582212209669b9e8a371aecabfd807ecd2391e4e880e72490ee467376e85a51eeb12fe4564736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b0319163317905561041f806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80633168a4d0146100515780639e7225c4146100bb578063d20ec0d5146100e0578063e308a886146100f3575b600080fd5b6100b961005f3660046102eb565b6002805460018181019092557fd35f1a30cf220fbb02dc852aaf21cae20338ac7d10d548ea52adafe8261a78730180546001600160a01b039093166001600160a01b03199093168317905560009182526020526040812055565b005b6100ce6100c93660046102eb565b610106565b60405190815260200160405180910390f35b6100b96100ee36600461030d565b610185565b6100b9610101366004610337565b61021b565b600254600090815b81811015610178576002818154811061012957610129610373565b6000918252602090912001546001600160a01b0385811691161415610166575050506001600160a01b031660009081526001602052604090205490565b806101708161039f565b91505061010e565b50633b9ac9ff9392505050565b6001600160a01b038216156102175760025460005b8181101561021457600281815481106101b5576101b5610373565b6000918252602090912001546001600160a01b0385811691161415610202576001600160a01b038416600090815260016020526040812080548592906101fc9084906103ba565b90915550505b8061020c8161039f565b91505061019a565b50505b5050565b60025460005b818110156102c8576002818154811061023c5761023c610373565b6000918252602090912001546001600160a01b03858116911614156102b6576001600160a01b038516600090815260016020526040812080548592906102839084906103d2565b90915550506001600160a01b038416600090815260016020526040812080548592906102b09084906103ba565b90915550505b806102c08161039f565b915050610221565b5050505050565b80356001600160a01b03811681146102e657600080fd5b919050565b6000602082840312156102fd57600080fd5b610306826102cf565b9392505050565b6000806040838503121561032057600080fd5b610329836102cf565b946020939093013593505050565b60008060006060848603121561034c57600080fd5b610355846102cf565b9250610363602085016102cf565b9150604084013590509250925092565b63b95aa35560e01b600052603260045260246000fd5b63b95aa35560e01b600052601160045260246000fd5b60006000198214156103b3576103b3610389565b5060010190565b600082198211156103cd576103cd610389565b500190565b6000828210156103e4576103e4610389565b50039056fea26469706673582212207bbd13d6b9db51b7b6c4f3ba83da83456d5d21f42f823118833b3c8b52284c3d64736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"add_balance\",\"outputs\":[],\"selector\":[819764462,3524182229],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"add_user\",\"outputs\":[],\"selector\":[760749681,828941520],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"query_balance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[106408263,2658280900],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[2]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[],\"selector\":[3198921928,3808995462],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

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

    public static UserContract load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new UserContract(contractAddress, client, credential);
    }

    public static UserContract deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(UserContract.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
