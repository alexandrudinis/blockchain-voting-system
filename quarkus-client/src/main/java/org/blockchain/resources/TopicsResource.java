package org.blockchain.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.blockchain.dto.OptionDTO;
import org.blockchain.dto.StoredOptionDTO;
import org.blockchain.dto.StoredTopicDTO;
import org.blockchain.dto.TopicDTO;
import org.blockchain.dto.VoteDTO;
import org.blockchain.dto.VotingResultDTO;
import org.blockchain.service.TopicsService;
import org.blockchain.utils.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.model.VotingSystem;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Alexandru Dinis
 */

@Path("/topics")
public class TopicsResource {

    private static final Logger LOG = LoggerFactory.getLogger(TopicsService.class);
    private final TopicsService topicsService;

    public TopicsResource(TopicsService topicsService) {
        this.topicsService = topicsService;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse createTopic(TopicDTO topicDTO) throws Exception {

        LOG.info("Will proceed to create the topic out of the DTO {}", topicDTO);

        TransactionReceipt transactionReceipt = topicsService.createTopic(topicDTO);

        LOG.info("Hash received from blockchain for topic creation out of dto {} is {}",
                topicDTO, transactionReceipt.getTransactionHash());

        return new JsonResponse(transactionReceipt.getTransactionHash());
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<StoredTopicDTO> getTopics() throws Exception {
        LOG.info("Will proceed to get all topics");
        return topicsService.getTopics();
    }

    @GET
    @Path("/options/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<StoredOptionDTO> getOptions(@PathParam("id") BigInteger topicId) throws Exception {
        LOG.info("Will proceed to get all options of topic {}", topicId);
        return topicsService.getOptions(topicId);
    }


    @GET
    @Path("/results")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<VotingResultDTO> getAllResults() throws Exception {
        LOG.info("Will proceed to get all voting results");
        return topicsService.getAllVotingResults();
    }



    @PUT
    @Path("/startVoting/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse enableStartVoting(@PathParam("id") BigInteger topicId) throws Exception {

        LOG.info("Will proceed to enable start voting for the topic {}", topicId);

        TransactionReceipt transactionReceipt = topicsService.enableStartVoting(topicId);

        LOG.info("Hash received from blockchain enable start voting for the topic {} is {}",
                topicId, transactionReceipt.getTransactionHash());

        return new JsonResponse(transactionReceipt.getTransactionHash());
    }

    @PUT
    @Path("/finishVoting/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse endVoting(@PathParam("id") BigInteger topicId) throws Exception {

        LOG.info("Will proceed to finish voting process for the topic {}", topicId);

        TransactionReceipt transactionReceipt = topicsService.endVoting(topicId);

        LOG.info("Hash received from blockchain finish voting process for the topic {} is {}",
                topicId, transactionReceipt.getTransactionHash());

        return new JsonResponse(transactionReceipt.getTransactionHash());
    }

    @POST
    @Path("/option")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse addOption(OptionDTO optionDTO) throws Exception {

        LOG.info("Will proceed to add option {}", optionDTO);

        TransactionReceipt transactionReceipt = topicsService.addOption(optionDTO);

        LOG.info("Hash received from blockchain finish add option {} is {}", optionDTO, transactionReceipt.getTransactionHash());

        return new JsonResponse(transactionReceipt.getTransactionHash());
    }

    @POST
    @Path("/vote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse vote(VoteDTO voteDTO) throws Exception {

        LOG.info("Will proceed to add option {}", voteDTO);

        TransactionReceipt transactionReceipt = topicsService.vote(voteDTO);

        LOG.info("Hash received from blockchain finish add option {} is {}", voteDTO, transactionReceipt.getTransactionHash());

        return new JsonResponse(transactionReceipt.getTransactionHash());
    }

}
