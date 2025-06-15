package app.store;

import com.google.gson.Gson;

import app.model.Student;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisStore {
    static JedisPool jedisPool;
    static Gson gson = new Gson();

    public static void init() {
        try {
            // Connection pool konfigürasyonu
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(20);
            poolConfig.setMaxIdle(10);
            poolConfig.setMinIdle(5);
            poolConfig.setTestOnBorrow(true);
            
            // Pool oluştur
            jedisPool = new JedisPool(poolConfig, "localhost", 6379);
            
            // Test connection ve veri ekleme
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.ping();
                System.out.println("Redis connection pool created successfully!");
                
                // Clear existing data
                jedis.flushDB();
                
                // Insert 10,000 records
                for (int i = 0; i < 10000; i++) {
                    String id = "2025" + String.format("%06d", i);
                    Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
                    jedis.set(id, gson.toJson(s));
                }
                
                System.out.println("Redis: 10,000 records inserted successfully!");
            }
            
        } catch (Exception e) {
            System.err.println("Redis connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Student get(String id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get(id);
            if (json == null) {
                return null;
            }
            return gson.fromJson(json, Student.class);
        } catch (Exception e) {
            System.err.println("Error getting from Redis: " + e.getMessage());
            return null;
        }
    }
}
