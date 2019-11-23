package nestedvar.Quiver.Utilities;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class Database{
    private static MongoClient client;
    public static MongoDatabase db;

    public static void connect() {
        MongoCredential credentials = MongoCredential.createCredential("Quiver", "admin", System.getenv("QUIVERSQLPASSWORD").toCharArray());
        client = new MongoClient(new ServerAddress("quiver.nestedvar.dev", 13370), Arrays.asList(credentials));
        db = client.getDatabase("Quiver");
    }

    public static MongoCollection<Document> getCollection(String collection) {
        return db.getCollection(collection);
    }

    public static void close() {
        client.close();
    }

}
