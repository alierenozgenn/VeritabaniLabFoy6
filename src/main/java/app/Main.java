package app;

import com.google.gson.Gson;

import app.store.HazelcastStore;
import app.store.MongoStore;
import app.store.RedisStore;
import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        port(8080);
        Gson gson = new Gson();

        System.out.println("Starting Redis...");
        try {
            RedisStore.init();
            System.out.println("Redis OK!");
        } catch (Exception e) {
            System.err.println("Redis FAILED: " + e.getMessage());
        }

        System.out.println("Starting Hazelcast...");
        try {
            HazelcastStore.init();
            System.out.println("Hazelcast OK!");
        } catch (Exception e) {
            System.err.println("Hazelcast FAILED: " + e.getMessage());
        }

        System.out.println("Starting MongoDB...");
        try {
            MongoStore.init();
            System.out.println("MongoDB OK!");
        } catch (Exception e) {
            System.err.println("MongoDB FAILED: " + e.getMessage());
        }

        System.out.println("Starting web server...");

        // Alternatif URL pattern - query parameter olarak
        get("/nosql-lab-rd", (req, res) -> {
            res.type("application/json");
            String studentId = req.queryParams("student_no");
            if (studentId == null) {
                res.status(400);
                return "{\"error\":\"student_no parameter required\"}";
            }
            System.out.println("Redis request for ID: " + studentId);
            return gson.toJson(RedisStore.get(studentId));
        });

        get("/nosql-lab-hz", (req, res) -> {
            res.type("application/json");
            String studentId = req.queryParams("student_no");
            if (studentId == null) {
                res.status(400);
                return "{\"error\":\"student_no parameter required\"}";
            }
            System.out.println("Hazelcast request for ID: " + studentId);
            return gson.toJson(HazelcastStore.get(studentId));
        });

        get("/nosql-lab-mon", (req, res) -> {
            res.type("application/json");
            String studentId = req.queryParams("student_no");
            if (studentId == null) {
                res.status(400);
                return "{\"error\":\"student_no parameter required\"}";
            }
            System.out.println("MongoDB request for ID: " + studentId);
            return gson.toJson(MongoStore.get(studentId));
        });

        // Test endpoint'i
        get("/test", (req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Server is working!\"}";
        });

        System.out.println("Web server started on port 8080!");
        System.out.println("Test URLs (query parameter format):");
        System.out.println("http://localhost:8080/nosql-lab-rd?student_no=2025000001");
        System.out.println("http://localhost:8080/nosql-lab-hz?student_no=2025000001");
        System.out.println("http://localhost:8080/nosql-lab-mon?student_no=2025000001");
    }
}
