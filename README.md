# blockchain-voting-system
Voting system using Ganache, Truffle, and Quarkus

## Ganache
- **Ganache** is a software tool widely used by developers to create a local blockchain network for testing and development purposes.
- Developers can effectively test different scenarios and troubleshoot their blockchain apps by simulating a blockchain network on their local machine.

## Truffle
### Configuration
1. Set the `truffle.config` to deploy on Ganache.
2. Configure your Quarkus application to connect to your local Ganache instance or a testnet. Specify the RPC endpoint URL of your Ethereum node in your application configuration. You can typically find this in your Ganache settings or by using the default endpoint: `http://localhost:7545`.

### Write Smart Contract Abstraction
- Use **Web3j** to generate Java wrappers for your Solidity smart contracts. You can do this using the Web3j command-line tool or Maven plugins.
- This step creates Java classes that serve as an abstraction layer for your contracts.

### Interact with the Contract
- Use the generated Java classes to interact with your Ethereum contract from your Quarkus application.
- Implement REST endpoints in your Quarkus application to handle requests for:
  - Creating topics
  - Adding options
  - Retrieving voting items
  - Viewing results
- Use the Web3j library to call the contract's functions and retrieve data from the blockchain.

Remember to consult the documentation of Web3j and Quarkus for detailed instructions on how to integrate Ethereum contract interaction into your Quarkus application.

