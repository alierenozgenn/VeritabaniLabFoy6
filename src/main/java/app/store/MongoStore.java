package app.store;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import app.model.Student;

public class MongoStore {
    static MongoClient client;
    static MongoCollection<Document> collection;
    static Gson gson = new Gson();

    public static void init() {
        try {
            client = MongoClients.create("mongodb://localhost:27017");
            collection = client.getDatabase("nosqllab").getCollection("students");
            
            // Clear existing data
            collection.drop();
            
            // Insert 10,000 records
            for (int i = 0; i < 10000; i++) {
                String id = "2025" + String.format("%06d", i);
                Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
                collection.insertOne(Document.parse(gson.toJson(s)));
            }
            
            System.out.println("MongoDB: 10,000 records inserted successfully!");
            
        } catch (Exception e) {
            System.err.println("MongoDB connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Student get(String id) {
        try {
            System.out.println("Getting from MongoDB: " + id);
            Document doc = collection.find(new Document("ogrenciNo", id)).first();
            if (doc == null) {
                System.out.println("MongoDB: No data found for ID: " + id);
                return null;
            }
            return gson.fromJson(doc.toJson(), Student.class);
        } catch (Exception e) {
            System.err.println("Error getting from MongoDB: " + e.getMessage());
            return null;
        }
    }
}
