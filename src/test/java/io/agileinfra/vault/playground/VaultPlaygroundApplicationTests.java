package io.agileinfra.vault.playground;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.vault.VaultContainer;

import static io.agileinfra.vault.playground.TestConstants.*;

@Testcontainers
@SpringBootTest
class VaultPlaygroundApplicationTests {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE_NAME))
                    .withNetworkAliases(POSTGRES_PRIVATE_ALIAS)
                    .withReuse(REUSE)
                    .withExposedPorts(POSTGRES_PRIVATE_PORT)
                    .withDatabaseName(POSTGRES_DATABASE_NAME)
                    .withUsername(POSTGRES_ROOT_USERNAME)
                    .withPassword(POSTGRES_ROOT_PASSWORD);
    @Container
    public static VaultContainer<?> vaultContainer =
            new VaultContainer<>(DockerImageName.parse(VAULT_IMAGE_NAME))
                    .withNetworkAliases(VAULT_PRIVATE_ALIAS)
                    .withVaultToken(VAULT_ROOT_TOKEN)
                    .withExposedPorts(VAULT_PRIVATE_PORT)
                    .withReuse(REUSE);

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
        registry.add("spring.cloud.vault.token", () -> VAULT_ROOT_TOKEN);
    }

    @Test
    void contextLoads() {
    }

}
