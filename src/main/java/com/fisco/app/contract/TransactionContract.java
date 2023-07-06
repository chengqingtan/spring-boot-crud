package com.fisco.app.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class TransactionContract extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506106fa806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806367e0badb146100465780638046f84a1461005c578063e1289cb314610071575b600080fd5b6001546040519081526020015b60405180910390f35b61006f61006a3660046104fb565b610095565b005b61008461007f366004610595565b610195565b6040516100539594939291906105fb565b6040805160a081018252868152602080820187815292820186905260608201859052608082018490526000805460018101825590805282517f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563600590920291820190815593518051939485949093610131937f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e564019201906103bf565b506040820151805161014d9160028401916020909101906103bf565b50606082015180516101699160038401916020909101906103bf565b506080820151816004015550506001806000828254610188919061064d565b9091555050505050505050565b60006060806060600080600087815481106101b2576101b2610673565b90600052602060002090600502016040518060a0016040529081600082015481526020016001820180546101e590610689565b80601f016020809104026020016040519081016040528092919081815260200182805461021190610689565b801561025e5780601f106102335761010080835404028352916020019161025e565b820191906000526020600020905b81548152906001019060200180831161024157829003601f168201915b5050505050815260200160028201805461027790610689565b80601f01602080910402602001604051908101604052809291908181526020018280546102a390610689565b80156102f05780601f106102c5576101008083540402835291602001916102f0565b820191906000526020600020905b8154815290600101906020018083116102d357829003601f168201915b5050505050815260200160038201805461030990610689565b80601f016020809104026020016040519081016040528092919081815260200182805461033590610689565b80156103825780601f1061035757610100808354040283529160200191610382565b820191906000526020600020905b81548152906001019060200180831161036557829003601f168201915b50505091835250506004919091015460209182015281519082015160408301516060840151608090940151929b919a509850919650945092505050565b8280546103cb90610689565b90600052602060002090601f0160209004810192826103ed5760008555610433565b82601f1061040657805160ff1916838001178555610433565b82800160010185558215610433579182015b82811115610433578251825591602001919060010190610418565b5061043f929150610443565b5090565b5b8082111561043f5760008155600101610444565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261047f57600080fd5b813567ffffffffffffffff8082111561049a5761049a610458565b604051601f8301601f19908116603f011681019082821181831017156104c2576104c2610458565b816040528381528660208588010111156104db57600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600080600060a0868803121561051357600080fd5b85359450602086013567ffffffffffffffff8082111561053257600080fd5b61053e89838a0161046e565b9550604088013591508082111561055457600080fd5b61056089838a0161046e565b9450606088013591508082111561057657600080fd5b506105838882890161046e565b95989497509295608001359392505050565b6000602082840312156105a757600080fd5b5035919050565b6000815180845260005b818110156105d4576020818501810151868301820152016105b8565b818111156105e6576000602083870101525b50601f01601f19169290920160200192915050565b85815260a06020820152600061061460a08301876105ae565b828103604084015261062681876105ae565b9050828103606084015261063a81866105ae565b9150508260808301529695505050505050565b6000821982111561066e57634e487b7160e01b600052601160045260246000fd5b500190565b634e487b7160e01b600052603260045260246000fd5b600181811c9082168061069d57607f821691505b602082108114156106be57634e487b7160e01b600052602260045260246000fd5b5091905056fea26469706673582212201f70ebe44c7589e9c0bc46d5278036fc08f8c5b1bd019322bc9fc907ab5626d264736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506106fe806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80631dc8475a146100465780635a8a27d814610073578063b7e65b1214610088575b600080fd5b61005961005436600461045c565b610099565b60405161006a9594939291906104c2565b60405180910390f35b6100866100813660046105b7565b6102c3565b005b60015460405190815260200161006a565b60006060806060600080600087815481106100b6576100b6610651565b90600052602060002090600502016040518060a0016040529081600082015481526020016001820180546100e990610667565b80601f016020809104026020016040519081016040528092919081815260200182805461011590610667565b80156101625780601f1061013757610100808354040283529160200191610162565b820191906000526020600020905b81548152906001019060200180831161014557829003601f168201915b5050505050815260200160028201805461017b90610667565b80601f01602080910402602001604051908101604052809291908181526020018280546101a790610667565b80156101f45780601f106101c9576101008083540402835291602001916101f4565b820191906000526020600020905b8154815290600101906020018083116101d757829003601f168201915b5050505050815260200160038201805461020d90610667565b80601f016020809104026020016040519081016040528092919081815260200182805461023990610667565b80156102865780601f1061025b57610100808354040283529160200191610286565b820191906000526020600020905b81548152906001019060200180831161026957829003601f168201915b50505091835250506004919091015460209182015281519082015160408301516060840151608090940151929b919a509850919650945092505050565b6040805160a081018252868152602080820187815292820186905260608201859052608082018490526000805460018101825590805282517fe0bab8f4d8172ba245190d13c94117e93b82166c25b2b69883350c192c90514060059092029182019081559351805193948594909361035f937fe0bab8f4d8172ba245190d13c94117e93b82166c25b2b69883350c192c905141019201906103c3565b506040820151805161037b9160028401916020909101906103c3565b50606082015180516103979160038401916020909101906103c3565b5060808201518160040155505060018060008282546103b691906106a2565b9091555050505050505050565b8280546103cf90610667565b90600052602060002090601f0160209004810192826103f15760008555610437565b82601f1061040a57805160ff1916838001178555610437565b82800160010185558215610437579182015b8281111561043757825182559160200191906001019061041c565b50610443929150610447565b5090565b5b808211156104435760008155600101610448565b60006020828403121561046e57600080fd5b5035919050565b6000815180845260005b8181101561049b5760208185018101518683018201520161047f565b818111156104ad576000602083870101525b50601f01601f19169290920160200192915050565b85815260a0602082015260006104db60a0830187610475565b82810360408401526104ed8187610475565b905082810360608401526105018186610475565b9150508260808301529695505050505050565b63b95aa35560e01b600052604160045260246000fd5b600082601f83011261053b57600080fd5b813567ffffffffffffffff8082111561055657610556610514565b604051601f8301601f19908116603f0116810190828211818310171561057e5761057e610514565b8160405283815286602085880101111561059757600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600080600060a086880312156105cf57600080fd5b85359450602086013567ffffffffffffffff808211156105ee57600080fd5b6105fa89838a0161052a565b9550604088013591508082111561061057600080fd5b61061c89838a0161052a565b9450606088013591508082111561063257600080fd5b5061063f8882890161052a565b95989497509295608001359392505050565b63b95aa35560e01b600052603260045260246000fd5b600181811c9082168061067b57607f821691505b6020821081141561069c5763b95aa35560e01b600052602260045260246000fd5b50919050565b600082198211156106c35763b95aa35560e01b600052601160045260246000fd5b50019056fea2646970667358221220be0600d794695ba263e0f8ca3cd55fd147437b7d61912687068b97e1c7db162164736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"conflictFields\":[{\"kind\":4,\"value\":[1]}],\"inputs\":[],\"name\":\"getNum\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[1742781147,3085327122],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":0,\"value\":[0]},{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"i\",\"type\":\"uint256\"}],\"name\":\"query_all_transaction\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[3777535155,499664730],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[0]},{\"kind\":4,\"value\":[1]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"pid\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"purchase_username\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"owner\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"transaction_date\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"price\",\"type\":\"uint256\"}],\"name\":\"record_transaction\",\"outputs\":[],\"selector\":[2152134730,1519003608],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_GETNUM = "getNum";

    public static final String FUNC_QUERY_ALL_TRANSACTION = "query_all_transaction";

    public static final String FUNC_RECORD_TRANSACTION = "record_transaction";

    protected TransactionContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public BigInteger getNum() throws ContractException {
        final Function function = new Function(FUNC_GETNUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public Tuple5<BigInteger, String, String, String, BigInteger> query_all_transaction(
            BigInteger i) throws ContractException {
        final Function function = new Function(FUNC_QUERY_ALL_TRANSACTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(i)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<BigInteger, String, String, String, BigInteger>(
                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue());
    }

    public TransactionReceipt record_transaction(BigInteger pid, String purchase_username,
            String owner, String transaction_date, BigInteger price) {
        final Function function = new Function(
                FUNC_RECORD_TRANSACTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(pid), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(purchase_username), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(owner), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(transaction_date), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRecord_transaction(BigInteger pid,
            String purchase_username, String owner, String transaction_date, BigInteger price) {
        final Function function = new Function(
                FUNC_RECORD_TRANSACTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(pid), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(purchase_username), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(owner), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(transaction_date), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String record_transaction(BigInteger pid, String purchase_username, String owner,
            String transaction_date, BigInteger price, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_RECORD_TRANSACTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(pid), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(purchase_username), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(owner), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(transaction_date), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple5<BigInteger, String, String, String, BigInteger> getRecord_transactionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_RECORD_TRANSACTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<BigInteger, String, String, String, BigInteger>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue()
                );
    }

    public static TransactionContract load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new TransactionContract(contractAddress, client, credential);
    }

    public static TransactionContract deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(TransactionContract.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
