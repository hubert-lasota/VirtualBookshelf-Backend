package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.auth.TestSecurityConfig;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Transactional
@Import(TestSecurityConfig.class)
class BookshelfControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:%d/api".formatted(port))
            .build();
    }


    @Test
    @WithMockUser(username = "test_user", roles = "USER")
    void shouldCreateBookshelf_whenRequestIsValid() {
        BookshelfRequest request = BookshelfTestDataProvider.getBookshelfRequest();

        webTestClient.post()
            .uri("/v1/bookshelves")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().exists("Location")
            .expectBody()
            .jsonPath("$.name").isEqualTo(request.name())
            .jsonPath("$.type").isEqualTo(request.type().name())
            .jsonPath("$.description").isEqualTo(request.description());
    }

}