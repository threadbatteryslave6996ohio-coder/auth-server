package dev.clippy.utils.envmanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnvFilesTest {
    @TempDir
    Path tempDir;

    @Test
    void loadsValuesFromExplicitDotenvFile() throws IOException {
        Path dotenvFile = tempDir.resolve("service.env");
        Files.writeString(dotenvFile, "AUTH_SERVER_PORT=9090\n");

        Map<String, String> values = EnvFiles.loadFile(dotenvFile);

        assertEquals("9090", values.get("AUTH_SERVER_PORT"));
    }

    @Test
    void loadRequiredFileRejectsMissingFile() {
        Path dotenvFile = tempDir.resolve("missing.env");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> EnvFiles.loadRequiredFile(dotenvFile));

        assertEquals("Env file is not present: " + dotenvFile.toAbsolutePath().normalize(), exception.getMessage());
    }
}
