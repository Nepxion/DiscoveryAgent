package com.nepxion.discovery.agent.config;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.nepxion.discovery.agent.logger.AgentLogger;
import com.nepxion.discovery.agent.plugin.AgentPath;

public enum Config {
    INSTANCE;

    private static AgentLogger LOGGER = AgentLogger.getLogger(Config.class.getName());

    private Properties config;

    public void initializeCoreConfig() {
        config = new Properties();

        try (final InputStreamReader configFileStream = loadConfig()) {
            config.load(configFileStream);
        } catch (Exception e) {
            LOGGER.warn("Failed to read the config file, will ignore config file");
        }
    }

    private static InputStreamReader loadConfig() throws ConfigNotFoundException {
        File configFile = new File(AgentPath.getPath(), "agent.config");

        if (configFile.exists() && configFile.isFile()) {
            try {
                LOGGER.info(String.format("Config file found in %s.", configFile));

                return new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                throw new ConfigNotFoundException("Failed to load agent.config", e);
            }
        }

        throw new ConfigNotFoundException("Failed to load agent.config");
    }

    public Properties getConfig() {
        if (null == config) {
            initializeCoreConfig();
        }

        return config;
    }
}