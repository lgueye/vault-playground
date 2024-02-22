package io.agileinfra.vault.playground;

public class TestConstants {
    public static final String POSTGRES_IMAGE_NAME = "postgres:16-alpine";
    public static final String POSTGRES_PRIVATE_ALIAS = "postgres";
    public static final int POSTGRES_PRIVATE_PORT = 5432;
    public static final String POSTGRES_DATABASE_NAME = "vault_playground_db";
    public static final String POSTGRES_ROOT_USERNAME = "vault_playground_root";
    public static final String POSTGRES_ROOT_PASSWORD = "s3cr3t_P@ssw0rd";
    public static final String VAULT_PRIVATE_ALIAS = "vault";
    public static final String VAULT_IMAGE_NAME = "vault:1.13.3";
    public static final String VAULT_ROOT_TOKEN = "00000000-0000-0000-0000-000000000000";
    public static final int VAULT_PRIVATE_PORT = 8200;
    public static final boolean REUSE = true;
}
