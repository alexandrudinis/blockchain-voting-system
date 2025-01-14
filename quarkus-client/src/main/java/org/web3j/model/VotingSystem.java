package org.web3j.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.10.0.
 */
@SuppressWarnings("rawtypes")
public class VotingSystem extends Contract {
    public static final String BINARY = "6080604052348015600e575f80fd5b506124818061001c5f395ff3fe608060405234801561000f575f80fd5b50600436106100e5575f3560e01c8063abe7c08e11610088578063c4ac65b711610063578063c4ac65b7146101ef578063cc89698c14610202578063dd61a65d14610213578063ea57d5d414610226575f80fd5b8063abe7c08e146101b4578063b384abef146101c7578063c3885d86146101da575f80fd5b806354110b9d116100c357806354110b9d14610149578063716588961461015e57806394c790bb146101815780639ac9585a14610194575f80fd5b806318d9adab146100e95780631ce30927146101145780633d2f5bda14610134575b5f80fd5b6100fc6100f7366004611d05565b610239565b60405161010b93929190611d4a565b60405180910390f35b610127610122366004611d73565b6102f9565b60405161010b9190611d93565b610147610142366004611d05565b610459565b005b61015161051f565b60405161010b9190611dd2565b61017161016c366004611d05565b610677565b60405161010b9493929190611e35565b61014761018f366004611e65565b6107d7565b6101a76101a2366004611d05565b6109eb565b60405161010b9190611e9e565b6101476101c2366004611d05565b610cac565b6101476101d5366004611d73565b610dc3565b6101e26110be565b60405161010b9190611f2d565b6101476101fd3660046120e6565b6114fb565b5f545b60405190815260200161010b565b610205610221366004612118565b6116a8565b610147610234366004612118565b61176a565b5f8181548110610247575f80fd5b905f5260205f2090600602015f91509050805f0180546102669061215c565b80601f01602080910402602001604051908101604052809291908181526020018280546102929061215c565b80156102dd5780601f106102b4576101008083540402835291602001916102dd565b820191905f5260205f20905b8154815290600101906020018083116102c057829003601f168201915b5050506005909301549192505060ff8082169161010090041683565b5f5460609083106103255760405162461bcd60e51b815260040161031c9061218e565b60405180910390fd5b5f8381548110610337576103376121bb565b905f5260205f20906006020160010180549050821061038f5760405162461bcd60e51b8152602060048201526014602482015273092dcecc2d8d2c840dee0e8d2dedc40d2dcc8caf60631b604482015260640161031c565b5f83815481106103a1576103a16121bb565b905f5260205f20906006020160010182815481106103c1576103c16121bb565b905f5260205f200180546103d49061215c565b80601f01602080910402602001604051908101604052809291908181526020018280546104009061215c565b801561044b5780601f106104225761010080835404028352916020019161044b565b820191905f5260205f20905b81548152906001019060200180831161042e57829003601f168201915b505050505090505b92915050565b5f5481106104795760405162461bcd60e51b815260040161031c9061218e565b5f818154811061048b5761048b6121bb565b5f91825260209091206005600690920201015460ff16156104be5760405162461bcd60e51b815260040161031c906121cf565b60015f82815481106104d2576104d26121bb565b5f9182526020822060069190910201600501805460ff19169215159290921790915560405182917fcf33babc496bb6dc2942b39cb7b75766bbbadf7da50d176ff8c513e99114023991a250565b5f80546060919067ffffffffffffffff81111561053e5761053e612047565b60405190808252806020026020018201604052801561058357816020015b604080518082019091525f81526060602082015281526020019060019003908161055c5790505b5090505f5b5f548110156106715760405180604001604052808281526020015f83815481106105b4576105b46121bb565b905f5260205f2090600602015f0180546105cd9061215c565b80601f01602080910402602001604051908101604052809291908181526020018280546105f99061215c565b80156106445780601f1061061b57610100808354040283529160200191610644565b820191905f5260205f20905b81548152906001019060200180831161062757829003601f168201915b505050505081525082828151811061065e5761065e6121bb565b6020908102919091010152600101610588565b50919050565b60605f805f8080549050851061069f5760405162461bcd60e51b815260040161031c9061218e565b5f85815481106106b1576106b16121bb565b905f5260205f2090600602015f015f86815481106106d1576106d16121bb565b905f5260205f209060060201600101805490505f87815481106106f6576106f66121bb565b5f9182526020822060056006909202010154815460ff909116919089908110610721576107216121bb565b905f5260205f20906006020160050160019054906101000a900460ff1683805461074a9061215c565b80601f01602080910402602001604051908101604052809291908181526020018280546107769061215c565b80156107c15780601f10610798576101008083540402835291602001916107c1565b820191905f5260205f20905b8154815290600101906020018083116107a457829003601f168201915b5050505050935093509350935093509193509193565b5f5482106107f75760405162461bcd60e51b815260040161031c9061218e565b6001600160a01b0381166108455760405162461bcd60e51b8152602060048201526015602482015274496e76616c696420766f746572206164647265737360581b604482015260640161031c565b5f8281548110610857576108576121bb565b5f91825260209091206005600690920201015460ff161561088a5760405162461bcd60e51b815260040161031c906121cf565b5f5b5f838154811061089e5761089e6121bb565b905f5260205f2090600602016002018054905081101561095d57816001600160a01b03165f84815481106108d4576108d46121bb565b905f5260205f20906006020160020182815481106108f4576108f46121bb565b5f918252602090912001546001600160a01b0316036109555760405162461bcd60e51b815260206004820152601860248201527f566f74657220616c726561647920726567697374657265640000000000000000604482015260640161031c565b60010161088c565b505f8281548110610970576109706121bb565b5f918252602080832060069290920290910160020180546001810182559083529181902090910180546001600160a01b0319166001600160a01b03841690811790915560405190815283917ff45b8429c36d478f9a6d081c4811a819b98aa5b608588bed9d406405cf28247391015b60405180910390a25050565b610a0e60405180606001604052805f815260200160608152602001606081525090565b5f548210610a2e5760405162461bcd60e51b815260040161031c9061218e565b5f808381548110610a4157610a416121bb565b905f5260205f209060060201600101805480602002602001604051908101604052809291908181526020015f905b82821015610b17578382905f5260205f20018054610a8c9061215c565b80601f0160208091040260200160405190810160405280929190818152602001828054610ab89061215c565b8015610b035780601f10610ada57610100808354040283529160200191610b03565b820191905f5260205f20905b815481529060010190602001808311610ae657829003601f168201915b505050505081526020019060010190610a6f565b5050505090505f815190505f8167ffffffffffffffff811115610b3c57610b3c612047565b604051908082528060200260200182016040528015610b8157816020015b604080518082019091525f815260606020820152815260200190600190039081610b5a5790505b5090505f5b82811015610be1576040518060400160405280828152602001858381518110610bb157610bb16121bb565b6020026020010151815250828281518110610bce57610bce6121bb565b6020908102919091010152600101610b86565b5060405180606001604052808681526020015f8781548110610c0557610c056121bb565b905f5260205f2090600602015f018054610c1e9061215c565b80601f0160208091040260200160405190810160405280929190818152602001828054610c4a9061215c565b8015610c955780601f10610c6c57610100808354040283529160200191610c95565b820191905f5260205f20905b815481529060010190602001808311610c7857829003601f168201915b505050918352505060200191909152949350505050565b5f548110610ccc5760405162461bcd60e51b815260040161031c9061218e565b5f8181548110610cde57610cde6121bb565b5f91825260209091206005600690920201015460ff16610d105760405162461bcd60e51b815260040161031c90612218565b5f8181548110610d2257610d226121bb565b905f5260205f20906006020160050160019054906101000a900460ff1615610d5c5760405162461bcd60e51b815260040161031c9061225d565b60015f8281548110610d7057610d706121bb565b5f9182526020822060056006909202010180549215156101000261ff00199093169290921790915560405182917f67dd2327eccab76e56070d042e4c2137556510de5aa667f62bf6145d678b9db991a250565b5f548210610de35760405162461bcd60e51b815260040161031c9061218e565b5f8281548110610df557610df56121bb565b5f91825260209091206005600690920201015460ff16610e275760405162461bcd60e51b815260040161031c90612218565b5f8281548110610e3957610e396121bb565b905f5260205f20906006020160050160019054906101000a900460ff1615610e735760405162461bcd60e51b815260040161031c9061225d565b610e7d8233611944565b610edf5760405162461bcd60e51b815260206004820152602d60248201527f596f7520617265206e6f74207265676973746572656420746f20766f7465206660448201526c6f72207468697320746f70696360981b606482015260840161031c565b5f8281548110610ef157610ef16121bb565b905f5260205f209060060201600101805490508110610f495760405162461bcd60e51b8152602060048201526014602482015273092dcecc2d8d2c840dee0e8d2dedc40d2dcc8caf60631b604482015260640161031c565b610f538233611a03565b15610fae5760405162461bcd60e51b815260206004820152602560248201527f596f75206861766520616c726561647920766f74656420666f72207468697320604482015264746f70696360d81b606482015260840161031c565b5f8281548110610fc057610fc06121bb565b905f5260205f2090600602016003018181548110610fe057610fe06121bb565b5f9182526020822001805491610ff5836122a7565b91905055505f828154811061100c5761100c6121bb565b5f918252602080832060046006909302019190910180546001810182559083529082200180546001600160a01b03191633179055805483908110611052576110526121bb565b905f5260205f2090600602016001018181548110611072576110726121bb565b905f5260205f20016040516110879190612339565b60405190819003812090339084907f909b43dcc56d91024768bbc5c8d56441234580b2fd6176844960cbc7218cc0b5905f90a45050565b5f80546060919067ffffffffffffffff8111156110dd576110dd612047565b60405190808252806020026020018201604052801561114157816020015b61112e6040518060a001604052805f815260200160608152602001606081526020015f151581526020015f151581525090565b8152602001906001900390816110fb5790505b5090505f5b5f54811015610671575f808281548110611162576111626121bb565b905f5260205f209060060201600101805480602002602001604051908101604052809291908181526020015f905b82821015611238578382905f5260205f200180546111ad9061215c565b80601f01602080910402602001604051908101604052809291908181526020018280546111d99061215c565b80156112245780601f106111fb57610100808354040283529160200191611224565b820191905f5260205f20905b81548152906001019060200180831161120757829003601f168201915b505050505081526020019060010190611190565b5050505090505f808381548110611251576112516121bb565b905f5260205f2090600602016003018054806020026020016040519081016040528092919081815260200182805480156112a857602002820191905f5260205f20905b815481526020019060010190808311611294575b505050505090505f825190505f8167ffffffffffffffff8111156112ce576112ce612047565b60405190808252806020026020018201604052801561132157816020015b61130e60405180606001604052805f8152602001606081526020015f81525090565b8152602001906001900390816112ec5790505b5090505f5b828110156113a0576040518060600160405280828152602001868381518110611351576113516121bb565b60200260200101518152602001858381518110611370576113706121bb565b602002602001015181525082828151811061138d5761138d6121bb565b6020908102919091010152600101611326565b506040518060a001604052808681526020015f87815481106113c4576113c46121bb565b905f5260205f2090600602015f0180546113dd9061215c565b80601f01602080910402602001604051908101604052809291908181526020018280546114099061215c565b80156114545780601f1061142b57610100808354040283529160200191611454565b820191905f5260205f20905b81548152906001019060200180831161143757829003601f168201915b505050505081526020018281526020015f8781548110611476576114766121bb565b905f5260205f2090600602016005015f9054906101000a900460ff16151581526020015f87815481106114ab576114ab6121bb565b905f5260205f20906006020160050160019054906101000a900460ff1615158152508686815181106114df576114df6121bb565b6020026020010181905250505050508080600101915050611146565b5f81511161154b5760405162461bcd60e51b815260206004820152601d60248201527f546f706963207469746c65206d757374206e6f7420626520656d707479000000604482015260640161031c565b6040805160e081018252828152606060208201819052918101829052808201829052608081018290525f60a0820181905260c082018190528054600181018255908052815183928392839260069091027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563019081906115ca9082612390565b5060208281015180516115e39260018501920190611baf565b50604082015180516115ff916002840191602090910190611c03565b506060820151805161161b916003840191602090910190611c62565b5060808201518051611637916004840191602090910190611c03565b5060a08201516005909101805460c09093015115156101000261ff00199215159290921661ffff19909316929092171790556040517fc4179300b6563ad720c3426c03189acc5a9efcd8c2cd58e7fd1cc3ed9ec3d44090611699908790611d93565b60405180910390a15050505050565b5f805483106116c95760405162461bcd60e51b815260040161031c9061218e565b5f8251116117195760405162461bcd60e51b815260206004820152601860248201527f4f7074696f6e206d757374206e6f7420626520656d7074790000000000000000604482015260640161031c565b5f6117248484611ab9565b90505f8481548110611738576117386121bb565b905f5260205f2090600602016003018181548110611758576117586121bb565b905f5260205f20015491505092915050565b5f54821061178a5760405162461bcd60e51b815260040161031c9061218e565b5f8151116117da5760405162461bcd60e51b815260206004820152601d60248201527f4f7074696f6e206e616d65206d757374206e6f7420626520656d707479000000604482015260640161031c565b60645f83815481106117ee576117ee6121bb565b905f5260205f20906006020160010180549050106118595760405162461bcd60e51b815260206004820152602260248201527f4d6178206f7074696f6e73207265616368656420666f72207468697320746f70604482015261696360f01b606482015260840161031c565b5f828154811061186b5761186b6121bb565b5f91825260209091206005600690920201015460ff161561189e5760405162461bcd60e51b815260040161031c906121cf565b5f82815481106118b0576118b06121bb565b5f918252602080832060016006909302018201805492830181558352909120016118da8282612390565b505f82815481106118ed576118ed6121bb565b5f91825260208083206003600690930201919091018054600181018255908352908220015560405182907f17d54b10d2f5efe6c5653d2c3f373803d477ce9962212280331f14843d8983fb906109df908490611d93565b5f805483106119655760405162461bcd60e51b815260040161031c9061218e565b5f5b5f8481548110611979576119796121bb565b905f5260205f209060060201600201805490508110156119fa57826001600160a01b03165f85815481106119af576119af6121bb565b905f5260205f20906006020160020182815481106119cf576119cf6121bb565b5f918252602090912001546001600160a01b0316036119f2576001915050610453565b600101611967565b505f9392505050565b5f80548310611a245760405162461bcd60e51b815260040161031c9061218e565b5f5b5f8481548110611a3857611a386121bb565b905f5260205f209060060201600401805490508110156119fa57826001600160a01b03165f8581548110611a6e57611a6e6121bb565b905f5260205f2090600602016004018281548110611a8e57611a8e6121bb565b5f918252602090912001546001600160a01b031603611ab1576001915050610453565b600101611a26565b5f80548310611ada5760405162461bcd60e51b815260040161031c9061218e565b5f5b5f8481548110611aee57611aee6121bb565b905f5260205f20906006020160010180549050811015611b735782805190602001205f8581548110611b2257611b226121bb565b905f5260205f2090600602016001018281548110611b4257611b426121bb565b905f5260205f2001604051611b579190612339565b604051809103902003611b6b579050610453565b600101611adc565b5060405162461bcd60e51b815260206004820152601060248201526f13dc1d1a5bdb881b9bdd08199bdd5b9960821b604482015260640161031c565b828054828255905f5260205f20908101928215611bf3579160200282015b82811115611bf35782518290611be39082612390565b5091602001919060010190611bcd565b50611bff929150611c9b565b5090565b828054828255905f5260205f20908101928215611c56579160200282015b82811115611c5657825182546001600160a01b0319166001600160a01b03909116178255602090920191600190910190611c21565b50611bff929150611cb7565b828054828255905f5260205f20908101928215611c56579160200282015b82811115611c56578251825591602001919060010190611c80565b80821115611bff575f611cae8282611ccb565b50600101611c9b565b5b80821115611bff575f8155600101611cb8565b508054611cd79061215c565b5f825580601f10611ce6575050565b601f0160209004905f5260205f2090810190611d029190611cb7565b50565b5f60208284031215611d15575f80fd5b5035919050565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b606081525f611d5c6060830186611d1c565b931515602083015250901515604090910152919050565b5f8060408385031215611d84575f80fd5b50508035926020909101359150565b602081525f611da56020830184611d1c565b9392505050565b805182525f602082015160406020850152611dca6040850182611d1c565b949350505050565b5f602082016020835280845180835260408501915060408160051b8601019250602086015f5b82811015611e2957603f19878603018452611e14858351611dac565b94506020938401939190910190600101611df8565b50929695505050505050565b608081525f611e476080830187611d1c565b60208301959095525091151560408301521515606090910152919050565b5f8060408385031215611e76575f80fd5b8235915060208301356001600160a01b0381168114611e93575f80fd5b809150509250929050565b60208152815160208201525f602083015160606040840152611ec36080840182611d1c565b6040850151848203601f19016060860152805180835291925060209081019181840191600582901b8501015f5b82811015611f2157601f19868303018452611f0c828651611dac565b60209586019594909401939150600101611ef0565b50979650505050505050565b5f602082016020835280845180835260408501915060408160051b8601019250602086015f5b82811015611e2957603f19878603018452815180518652602081015160a06020880152611f8360a0880182611d1c565b90506040820151878203604089015281815180845260208401915060208160051b8501016020840193505f5b8281101561200257601f19868303018452845180518352602081015160606020850152611fdf6060850182611d1c565b604092830151949092019390935260209586019594909401939150600101611faf565b506060860151945061201860608c018615159052565b6080860151955061202d60808c018715159052565b995050506020968701969490940193505050600101611f53565b634e487b7160e01b5f52604160045260245ffd5b5f82601f83011261206a575f80fd5b813567ffffffffffffffff81111561208457612084612047565b604051601f8201601f19908116603f0116810167ffffffffffffffff811182821017156120b3576120b3612047565b6040528181528382016020018510156120ca575f80fd5b816020850160208301375f918101602001919091529392505050565b5f602082840312156120f6575f80fd5b813567ffffffffffffffff81111561210c575f80fd5b611dca8482850161205b565b5f8060408385031215612129575f80fd5b82359150602083013567ffffffffffffffff811115612146575f80fd5b6121528582860161205b565b9150509250929050565b600181811c9082168061217057607f821691505b60208210810361067157634e487b7160e01b5f52602260045260245ffd5b602080825260139082015272092dcecc2d8d2c840e8dee0d2c640d2dcc8caf606b1b604082015260600190565b634e487b7160e01b5f52603260045260245ffd5b60208082526029908201527f566f74696e672068617320616c7265616479207374617274656420666f72207460408201526868697320746f70696360b81b606082015260800190565b60208082526025908201527f566f74696e6720686173206e6f74207374617274656420666f72207468697320604082015264746f70696360d81b606082015260800190565b6020808252602a908201527f566f74696e672068617320616c72656164792066696e697368656420666f72206040820152697468697320746f70696360b01b606082015260800190565b5f600182016122c457634e487b7160e01b5f52601160045260245ffd5b5060010190565b5f81546122d78161215c565b6001821680156122ee576001811461230357612330565b60ff1983168652811515820286019350612330565b845f5260205f205f5b838110156123285781548882015260019091019060200161230c565b505081860193505b50505092915050565b5f611da582846122cb565b601f82111561238b57805f5260205f20601f840160051c810160208510156123695750805b601f840160051c820191505b81811015612388575f8155600101612375565b50505b505050565b815167ffffffffffffffff8111156123aa576123aa612047565b6123be816123b8845461215c565b84612344565b6020601f8211600181146123f0575f83156123d95750848201515b5f19600385901b1c1916600184901b178455612388565b5f84815260208120601f198516915b8281101561241f57878501518255602094850194600190920191016123ff565b508482101561243c57868401515f19600387901b60f8161c191681555b50505050600190811b0190555056fea26469706673582212206f08d7dd7a8c13d40c962dfda907dae2143069dd14ab506dad75860794408d3e64736f6c634300081a0033";

    public static final String FUNC_ADDOPTION = "addOption";

    public static final String FUNC_ADDTOPIC = "addTopic";

    public static final String FUNC_FINISHVOTING = "finishVoting";

    public static final String FUNC_GETOPTION = "getOption";

    public static final String FUNC_GETTOPIC = "getTopic";

    public static final String FUNC_GETTOPICCOUNT = "getTopicCount";

    public static final String FUNC_GETTOPICWITHOPTIONS = "getTopicWithOptions";

    public static final String FUNC_GETTOPICSONLY = "getTopicsOnly";

    public static final String FUNC_GETTOPICSWITHDETAILS = "getTopicsWithDetails";

    public static final String FUNC_GETVOTECOUNT = "getVoteCount";

    public static final String FUNC_REGISTERVOTER = "registerVoter";

    public static final String FUNC_STARTVOTING = "startVoting";

    public static final String FUNC_TOPICS = "topics";

    public static final String FUNC_VOTE = "vote";

    public static final Event OPTIONADDED_EVENT = new Event("OptionAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TOPICADDED_EVENT = new Event("TopicAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event VOTERREGISTERED_EVENT = new Event("VoterRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event VOTINGFINISHED_EVENT = new Event("VotingFinished", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event VOTINGSTARTED_EVENT = new Event("VotingStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected VotingSystem(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VotingSystem(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VotingSystem(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VotingSystem(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<OptionAddedEventResponse> getOptionAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OPTIONADDED_EVENT, transactionReceipt);
        ArrayList<OptionAddedEventResponse> responses = new ArrayList<OptionAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OptionAddedEventResponse typedResponse = new OptionAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.option = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OptionAddedEventResponse getOptionAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OPTIONADDED_EVENT, log);
        OptionAddedEventResponse typedResponse = new OptionAddedEventResponse();
        typedResponse.log = log;
        typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.option = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OptionAddedEventResponse> optionAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOptionAddedEventFromLog(log));
    }

    public Flowable<OptionAddedEventResponse> optionAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OPTIONADDED_EVENT));
        return optionAddedEventFlowable(filter);
    }

    public static List<TopicAddedEventResponse> getTopicAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOPICADDED_EVENT, transactionReceipt);
        ArrayList<TopicAddedEventResponse> responses = new ArrayList<TopicAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TopicAddedEventResponse typedResponse = new TopicAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topic = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TopicAddedEventResponse getTopicAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOPICADDED_EVENT, log);
        TopicAddedEventResponse typedResponse = new TopicAddedEventResponse();
        typedResponse.log = log;
        typedResponse.topic = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TopicAddedEventResponse> topicAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTopicAddedEventFromLog(log));
    }

    public Flowable<TopicAddedEventResponse> topicAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOPICADDED_EVENT));
        return topicAddedEventFlowable(filter);
    }

    public static List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.voter = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.option = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotedEventResponse getVotedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTED_EVENT, log);
        VotedEventResponse typedResponse = new VotedEventResponse();
        typedResponse.log = log;
        typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.voter = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.option = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<VotedEventResponse> votedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotedEventFromLog(log));
    }

    public Flowable<VotedEventResponse> votedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventFlowable(filter);
    }

    public static List<VoterRegisteredEventResponse> getVoterRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTERREGISTERED_EVENT, transactionReceipt);
        ArrayList<VoterRegisteredEventResponse> responses = new ArrayList<VoterRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoterRegisteredEventResponse typedResponse = new VoterRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.voterAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VoterRegisteredEventResponse getVoterRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTERREGISTERED_EVENT, log);
        VoterRegisteredEventResponse typedResponse = new VoterRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.voterAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<VoterRegisteredEventResponse> voterRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVoterRegisteredEventFromLog(log));
    }

    public Flowable<VoterRegisteredEventResponse> voterRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTERREGISTERED_EVENT));
        return voterRegisteredEventFlowable(filter);
    }

    public static List<VotingFinishedEventResponse> getVotingFinishedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTINGFINISHED_EVENT, transactionReceipt);
        ArrayList<VotingFinishedEventResponse> responses = new ArrayList<VotingFinishedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotingFinishedEventResponse typedResponse = new VotingFinishedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotingFinishedEventResponse getVotingFinishedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTINGFINISHED_EVENT, log);
        VotingFinishedEventResponse typedResponse = new VotingFinishedEventResponse();
        typedResponse.log = log;
        typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<VotingFinishedEventResponse> votingFinishedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotingFinishedEventFromLog(log));
    }

    public Flowable<VotingFinishedEventResponse> votingFinishedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTINGFINISHED_EVENT));
        return votingFinishedEventFlowable(filter);
    }

    public static List<VotingStartedEventResponse> getVotingStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTINGSTARTED_EVENT, transactionReceipt);
        ArrayList<VotingStartedEventResponse> responses = new ArrayList<VotingStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotingStartedEventResponse typedResponse = new VotingStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotingStartedEventResponse getVotingStartedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTINGSTARTED_EVENT, log);
        VotingStartedEventResponse typedResponse = new VotingStartedEventResponse();
        typedResponse.log = log;
        typedResponse.topicIndex = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<VotingStartedEventResponse> votingStartedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotingStartedEventFromLog(log));
    }

    public Flowable<VotingStartedEventResponse> votingStartedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTINGSTARTED_EVENT));
        return votingStartedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addOption(BigInteger _topicIndex, String _option) {
        final Function function = new Function(
                FUNC_ADDOPTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex), 
                new org.web3j.abi.datatypes.Utf8String(_option)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addTopic(String _topic) {
        final Function function = new Function(
                FUNC_ADDTOPIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_topic)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finishVoting(BigInteger _topicIndex) {
        final Function function = new Function(
                FUNC_FINISHVOTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getOption(BigInteger _topicIndex, BigInteger _optionIndex) {
        final Function function = new Function(FUNC_GETOPTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(_optionIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, Boolean, Boolean>> getTopic(BigInteger _topicIndex) {
        final Function function = new Function(FUNC_GETTOPIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<String, BigInteger, Boolean, Boolean>>(function,
                new Callable<Tuple4<String, BigInteger, Boolean, Boolean>>() {
                    @Override
                    public Tuple4<String, BigInteger, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getTopicCount() {
        final Function function = new Function(FUNC_GETTOPICCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TopicWithOptionInfo> getTopicWithOptions(BigInteger _topicIndex) {
        final Function function = new Function(FUNC_GETTOPICWITHOPTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<TopicWithOptionInfo>() {}));
        return executeRemoteCallSingleValueReturn(function, TopicWithOptionInfo.class);
    }

    public RemoteFunctionCall<List> getTopicsOnly() {
        final Function function = new Function(FUNC_GETTOPICSONLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<TopicInfo>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getTopicsWithDetails() {
        final Function function = new Function(FUNC_GETTOPICSWITHDETAILS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<VotingSystem.TopicDetails>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getVoteCount(BigInteger _topicIndex, String _option) {
        final Function function = new Function(FUNC_GETVOTECOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex), 
                new org.web3j.abi.datatypes.Utf8String(_option)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerVoter(BigInteger _topicIndex, String _voterAddress) {
        final Function function = new Function(
                FUNC_REGISTERVOTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex), 
                new org.web3j.abi.datatypes.Address(160, _voterAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> startVoting(BigInteger _topicIndex) {
        final Function function = new Function(
                FUNC_STARTVOTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<String, Boolean, Boolean>> topics(BigInteger param0) {
        final Function function = new Function(FUNC_TOPICS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple3<String, Boolean, Boolean>>(function,
                new Callable<Tuple3<String, Boolean, Boolean>>() {
                    @Override
                    public Tuple3<String, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger _topicIndex, BigInteger _optionIndex) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_topicIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(_optionIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static VotingSystem load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingSystem(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VotingSystem load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingSystem(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VotingSystem load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VotingSystem(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VotingSystem load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VotingSystem(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VotingSystem> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingSystem.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VotingSystem> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingSystem.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<VotingSystem> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingSystem.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VotingSystem> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingSystem.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OptionInfo extends DynamicStruct {
        public BigInteger id;

        public String name;

        public OptionInfo(BigInteger id, String name) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(name));
            this.id = id;
            this.name = name;
        }

        public OptionInfo(Uint256 id, Utf8String name) {
            super(id, name);
            this.id = id.getValue();
            this.name = name.getValue();
        }
    }

    public static class TopicInfo extends DynamicStruct {
        public BigInteger id;

        public String title;

        public TopicInfo(BigInteger id, String title) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(title));
            this.id = id;
            this.title = title;
        }

        public TopicInfo(Uint256 id, Utf8String title) {
            super(id, title);
            this.id = id.getValue();
            this.title = title.getValue();
        }
    }

    public static class OptionDetails extends DynamicStruct {
        public BigInteger id;

        public String option;

        public BigInteger numberOfVotes;

        public OptionDetails(BigInteger id, String option, BigInteger numberOfVotes) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(option), 
                    new org.web3j.abi.datatypes.generated.Uint256(numberOfVotes));
            this.id = id;
            this.option = option;
            this.numberOfVotes = numberOfVotes;
        }

        public OptionDetails(Uint256 id, Utf8String option, Uint256 numberOfVotes) {
            super(id, option, numberOfVotes);
            this.id = id.getValue();
            this.option = option.getValue();
            this.numberOfVotes = numberOfVotes.getValue();
        }
    }

    public static class TopicWithOptionInfo extends DynamicStruct {
        public BigInteger id;

        public String title;

        public List<OptionInfo> options;

        public TopicWithOptionInfo(BigInteger id, String title, List<OptionInfo> options) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(title), 
                    new org.web3j.abi.datatypes.DynamicArray<OptionInfo>(OptionInfo.class, options));
            this.id = id;
            this.title = title;
            this.options = options;
        }

        public TopicWithOptionInfo(Uint256 id, Utf8String title, @Parameterized(type = OptionInfo.class) DynamicArray<OptionInfo> options) {
            super(id, title, options);
            this.id = id.getValue();
            this.title = title.getValue();
            this.options = options.getValue();
        }
    }

    public static class TopicDetails extends DynamicStruct {
        public BigInteger id;

        public String title;

        public List<OptionDetails> options;

        public Boolean started;

        public Boolean finished;

        public TopicDetails(BigInteger id, String title, List<OptionDetails> options, Boolean started, Boolean finished) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(title), 
                    new org.web3j.abi.datatypes.DynamicArray<OptionDetails>(OptionDetails.class, options), 
                    new org.web3j.abi.datatypes.Bool(started), 
                    new org.web3j.abi.datatypes.Bool(finished));
            this.id = id;
            this.title = title;
            this.options = options;
            this.started = started;
            this.finished = finished;
        }

        public TopicDetails(Uint256 id, Utf8String title, @Parameterized(type = OptionDetails.class) DynamicArray<OptionDetails> options, Bool started, Bool finished) {
            super(id, title, options, started, finished);
            this.id = id.getValue();
            this.title = title.getValue();
            this.options = options.getValue();
            this.started = started.getValue();
            this.finished = finished.getValue();
        }
    }

    public static class OptionAddedEventResponse extends BaseEventResponse {
        public BigInteger topicIndex;

        public String option;
    }

    public static class TopicAddedEventResponse extends BaseEventResponse {
        public String topic;
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public BigInteger topicIndex;

        public String voter;

        public byte[] option;
    }

    public static class VoterRegisteredEventResponse extends BaseEventResponse {
        public BigInteger topicIndex;

        public String voterAddress;
    }

    public static class VotingFinishedEventResponse extends BaseEventResponse {
        public BigInteger topicIndex;
    }

    public static class VotingStartedEventResponse extends BaseEventResponse {
        public BigInteger topicIndex;
    }
}
