package org.blockchain.resources;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.blockchain.dto.VoterDTO;
import org.blockchain.service.VotersService;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class VotersResourceTest {
    @InjectMock
    VotersService votersService;

    @Test
    public void testRegisterVoter() throws Exception {
        VoterDTO voterDTO = new VoterDTO();
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votersService.registerVoter(any(VoterDTO.class))).thenReturn(transactionReceipt);

        given()
                .contentType(ContentType.JSON)
                .body(voterDTO)
                .when()
                .post("/voters/register")
                .then()
                .statusCode(200)
                .body("message", equalTo("sampleHash"));
    }
}
