package dev.clippy.auth;

import dev.clippy.utils.envmanager.EnvFiles;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Default standalone entry point for the auth server. This is the "actor" that decides how the
 * environment is fetched (here: a {@code .env} file discovered from the working directory) and hands
 * the resolved values to {@link ClippyAuthServerApplication#start(Map, String[])}. Swap this class
 * for a different strategy (system env, Vault, k8s, ...) without touching the core.
 */
public final class ClippyAuthServerLauncher {
    private ClippyAuthServerLauncher() {
    }

    public static void main(String[] args) throws IOException {
        ClippyAuthServerApplication.start(resolveEnvironment(), args);
    }

    static Map<String, String> resolveEnvironment() throws IOException {
        return resolveEnvironment(Path.of("").toAbsolutePath());
    }

    static Map<String, String> resolveEnvironment(Path startDirectory) throws IOException {
        return EnvFiles.loadDotenvOnly(startDirectory);
    }
}
