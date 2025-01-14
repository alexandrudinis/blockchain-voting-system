package org.blockchain.producers;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;


/**
 * @author Alexandru Dinis
 *
 * This is an implementation of the web3j client.
 * It's needed because the web3j it's an interface, and Quarkus will not know what to inject, as there is no implementation
 */
public class Web3jProducer {
    private static final Logger LOG = LoggerFactory.getLogger(Web3jProducer.class);

    private final String ganacheRpcURL;

    public Web3jProducer(@ConfigProperty(name = "ethereum.web3j.client-address") String ganacheRpcURL) {
        this.ganacheRpcURL = ganacheRpcURL;
    }

    @Produces
    @Singleton
    public Web3j produceWeb3j() {
        LOG.debug("Will build web3j client using ganache repo url {}", ganacheRpcURL);
        return Web3j.build(new HttpService(ganacheRpcURL));
    }
}
