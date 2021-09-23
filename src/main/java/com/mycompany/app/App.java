package com.mycompany.app;


import java.net.UnknownHostException;

//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;


import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        // Create seed data

        List<Document> seedData = new ArrayList<Document>();

        seedData.add(new Document("decade", "1970s")
            .append("artist", "Debby Boone")
            .append("song", "You Light Up My Life")
            .append("weeksAtOne", 10)
        );

        seedData.add(new Document("decade", "1980s")
            .append("artist", "Olivia Newton-John")
            .append("song", "Physical")
            .append("weeksAtOne", 10)
        );

        seedData.add(new Document("decade", "1990s")
            .append("artist", "Mariah Carey")
            .append("song", "One Sweet Day")
            .append("weeksAtOne", 16)
        );

        // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
/*
        MongoClientURI uri  = new MongoClientURI("mongodb+srv://jdellaria:Eff13m23@cluster0.ian4z.mongodb.net/JonsDatabase?retryWrites=true&w=majority");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
*/

/*
        ConnectionString connectionString = new ConnectionString("mongodb+srv://jdellaria:Eff13m23@cluster0.ian4z.mongodb.net/JonsDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient client = MongoClients.create(settings);

        MongoClient mongoClient = client.create();
        MongoDatabase db = client.getDatabase("JonsDatabase");
        */

        String uri = "mongodb+srv://jdellaria:Eff13m23@cluster0.ian4z.mongodb.net/JonsDatabase?retryWrites=true&w=majority";
                try (MongoClient client = MongoClients.create(uri)) {
                    MongoDatabase db = client.getDatabase("JonsDatabase");
                    try {
  //                      Bson command = new BsonDocument("ping", new BsonInt64(1));
    //                    Document commandResult = database.runCommand(command);
                        System.out.println("Connected successfully to server.");

                        /*
                         * First we'll add a few songs. Nothing is required to create the
                         * songs collection; it is created automatically when we insert.
                         */

  //                      MongoCollection<Document> songs = db.getCollection("songs");
                        MongoCollection<Document> songs = db.getCollection("JonsCollection");
                        System.out.println("Connected to JonsCollection.");

                        // Note that the insert method can take either an array or a document.

  //                      songs.insertMany(seedData);

                        /*
                         * Then we need to give Boyz II Men credit for their contribution to
                         * the hit "One Sweet Day".
                         */

  //                      Document updateQuery = new Document("song", "One Sweet Day");
  //                      songs.updateOne(updateQuery, new Document("$set", new Document("artist", "Mariah Carey ft. Boyz II Men")));

                        /*
                         * Finally we run a query which returns all the hits that spent 10
                         * or more weeks at number 1.
                         */

//                        Document findQuery = new Document("artist", new Document("$eq","Van Halen"));
                        Document findQuery = new Document("Artist", "Van Halen");

//                        Bson filter = eq("Artist", "Van Halen");

                        Bson filter = new Document("Artist", "Van Halen");

                        System.out.println("filter:" + filter);
                        System.out.println("findQuery:" + findQuery);
//                        Document orderBy = new Document("decade", 1);

//                        MongoCursor<Document> cursor = songs.find(findQuery).sort(orderBy).iterator();
//                        MongoCursor<Document> cursor = songs.find(findQuery).iterator();
//                        MongoCursor<Document> cursor = songs.find(filter).iterator();
      //                  MongoCursor<Document> cursorExec = songs.find(findQuery);
//                        db.products.find( { "available" : "true" } ).explain()
                        MongoCursor<Document> cursor = songs.find(findQuery).iterator();
                        MongoCollection<BsonDocument> collection = client.getDatabase("JonsDatabase")
                                .getCollection("explainTest", BsonDocument.class);

                        FindIterable<BsonDocument> iterable = collection.find(findQuery);

        //                Document explainDocument = iterable.explain();
       //                 MongoCursor<Document> cursor = songs.find( { "available" : "true" } ).explain();
//                        cursor.explain("executionStats");
//                        FindIterable<Document> cursor1 = songs.find(findQuery).;
                        System.out.println("cursor.");
                        int x = 0;
                        try {
//                          cursor.explain("executionStats");
                            while (cursor.hasNext() && (x < 10)) {
                                Document doc = cursor.next();
                                System.out.println(
                                    "Artist: " + doc.get("Artist") + ", Song: " + doc.get("Name")
                                );
                                x++;

  /*                              System.out.println(
    "In the " + doc.get("decade") + ", " + doc.get("song") +
    " by " + doc.get("artist") + " topped the charts for " +
    doc.get("weeksAtOne") + " straight weeks."
);*/
                            }
                        } finally {
                            cursor.close();
                        }

                        // Since this is an example, we'll clean up after ourselves.

                //        songs.drop();

                        // Only close the connection when your app is terminating

                        client.close();



                    } catch (MongoException me) {
                        System.err.println("An error occurred while attempting to run a command: " + me);
                    }
                }




    }
}
