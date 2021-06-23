package io.github.sandornemeth.rabbitmq.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.rabbitmq.http.client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InvalidRabbitmqGetQueueResponseExampleApplicationTests {

    private WireMockServer wireMockServer;

    private Client clientUnderTest;

    @BeforeEach
    void setup() throws Exception {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();

        clientUnderTest = new Client("http://localhost:" + wireMockServer.port() + "/", "user", "password");
    }

    @AfterEach
    void teardown() throws Exception {
        wireMockServer.stop();
    }

    @Test
    void demonstrate() throws Exception {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/queues/%2F/aqueue"))
                .withBasicAuth("user", "password")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBody(invalidRabbitMqResponse())));

        assertDoesNotThrow(() -> {
            clientUnderTest.getQueue("/", "aqueue");
        });
    }

    byte[] invalidRabbitMqResponse() throws Exception {
        URI responseUri = Objects.requireNonNull(getClass().getResource("/invalid_rabbitmq_response.json")).toURI();
        return Files.readAllBytes(Paths.get(responseUri));
    }


}
