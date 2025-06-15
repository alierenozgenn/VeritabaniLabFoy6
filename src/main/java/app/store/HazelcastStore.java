package app.store;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import app.model.Student;

public class HazelcastStore {
    static HazelcastInstance hz;
    static IMap<String, Student> map;

    public static void init() {
        try {
            // Client konfigürasyonu oluştur
            ClientConfig clientConfig = new ClientConfig();
            
            // Cluster name'i "dev" yap (Docker loglarından gördük)
            clientConfig.getGroupConfig().setName("dev");
            clientConfig.getGroupConfig().setPassword("dev-pass");
            
            // Network konfigürasyonu
            clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");
            
            hz = HazelcastClient.newHazelcastClient(clientConfig);
            map = hz.getMap("ogrenciler");
            
            // Clear existing data
            map.clear();
            
            // 10,000 kayıt ekle
            for (int i = 0; i < 10000; i++) {
                String id = "2025" + String.format("%06d", i);
                Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
                map.put(id, s);
            }
            
            System.out.println("Hazelcast initialized with 10,000 records!");
            
        } catch (Exception e) {
            System.err.println("Hazelcast connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Student get(String id) {
        try {
            return map.get(id);
        } catch (Exception e) {
            System.err.println("Error getting from Hazelcast: " + e.getMessage());
            return null;
        }
    }
}
