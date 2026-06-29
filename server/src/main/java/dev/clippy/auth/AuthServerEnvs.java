package dev.clippy.auth;

import dev.clippy.utils.envmanager.Env;
import dev.clippy.utils.envmanager.EnvOption;
import dev.clippy.utils.envmanager.EnvSchema;
import dev.clippy.utils.envmanager.EnvType;

import java.util.HashMap;
import java.util.Map;

public final class AuthServerEnvs {
    public static final EnvOption<String> AUTH_DATASOURCE_URL;
    public static final EnvOption<String> AUTH_DATASOURCE_USERNAME;
    public static final EnvOption<String> AUTH_DATASOURCE_PASSWORD;
    public static final EnvOption<String> AUTH_SERVER_PORT;
    public static final EnvOption<String> AUTH_LOGGING_FILE_NAME;
    public static final EnvSchema ENV;

    static {
        var builder = EnvSchema.builder();
        AUTH_DATASOURCE_URL = builder.optional("AUTH_DATASOURCE_URL", EnvType.string(), "jdbc:postgresql://localhost:5433/auth");
        AUTH_DATASOURCE_USERNAME = builder.optional("AUTH_DATASOURCE_USERNAME", EnvType.string(), "auth");
        AUTH_DATASOURCE_PASSWORD = builder.optional("AUTH_DATASOURCE_PASSWORD", EnvType.string(), "auth");
        AUTH_SERVER_PORT = builder.optional("AUTH_SERVER_PORT", EnvType.string(), "8081");
        AUTH_LOGGING_FILE_NAME = builder.optional("AUTH_LOGGING_FILE_NAME", EnvType.string(), "logs/clippy-auth-server.log");
        ENV = builder.build();
    }

    private AuthServerEnvs() {
    }

    public static Env from(Map<String, String> source) {
        return ENV.from(source);
    }

    public static Map<String, Object> springDefaults(Env env) {
        Map<String, Object> values = new HashMap<>();
        values.put("spring.datasource.url", env.get(AUTH_DATASOURCE_URL));
        values.put("spring.datasource.username", env.get(AUTH_DATASOURCE_USERNAME));
        values.put("spring.datasource.password", env.get(AUTH_DATASOURCE_PASSWORD));
        values.put("server.port", env.get(AUTH_SERVER_PORT));
        values.put("logging.file.name", env.get(AUTH_LOGGING_FILE_NAME));
        return values;
    }
}
