package org.blockchain.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.blockchain.dto.VoterDTO;
import org.blockchain.service.VotersService;
import org.blockchain.utils.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * @author Alexandru Dinis
 */

@Path("/voters")
public class VotersResource {

    private static final Logger LOG = LoggerFactory.getLogger(VotersResource.class);
    private final VotersService votersService;

    public VotersResource(VotersService votersService) {
        this.votersService = votersService;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse registerVoter(VoterDTO voterDTO) throws Exception {
        LOG.info("Will proceed to register the voter out of the dto {}", voterDTO);

        TransactionReceipt transactionReceipt = votersService.registerVoter(voterDTO);

        LOG.info("Hash received from blockchain for register voter out of dto {} is {}",
                voterDTO, transactionReceipt.getTransactionHash());

        return new JsonResponse(transactionReceipt.getTransactionHash());
    }
}
