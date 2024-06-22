package com.valkeon.vmongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public final class VMongoDB extends JavaPlugin {

    private static VMongoDB instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        String host = getConfig().getString("host", "localhost");
        int port = getConfig().getInt("port", 27017);
        String dbName = getConfig().getString("database", "minecraft");

        String uri = "mongodb://" + host + ":" + port;
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbName);
        getLogger().info("Database connected!");
    }

    @Override
    public void onDisable() {
        if (mongoClient != null) {
            mongoClient.close();
            getLogger().info("Database connection closed!");
        }
        instance = null; // Clear the instance
    }

    public static VMongoDB getInstance() {
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
