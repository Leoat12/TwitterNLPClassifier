package br.unirio.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import br.unirio.models.*;

public class TweetTagger {
    private CRFClassifier classifier;

    //TODO: Reformar a classe de acordo com a estrutura que será feito no programa.
    public TweetTagger(String modelPath){
        ToolProperties tprops = ToolProperties.getInstance();

        File model = new File(tprops.getModelpath());
        Properties props = new Properties();
        props.setProperty("inputEncoding", "UTF-8");
        props.setProperty("outputEncoding", "UTF-8");
        SeqClassifierFlags flags = new SeqClassifierFlags(props);
        this.classifier = new CRFClassifier(flags);
        try {
            classifier.loadClassifier(model);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //TODO: Ver o que fazer com este método. Colocar uma função manual afinal? 
    public ArrayList<Tweet> tagFromJSONFile(String filepath){
        FileInputStream fstream;
        System.out.println("Classifying...");
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            fstream = new FileInputStream(filepath);
            InputStreamReader reader = new InputStreamReader(fstream);

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Tweet>>(){}.getType();
            ArrayList<Tweet> tweets = gson.fromJson(reader, listType);

            ArrayList<Tweet> taggedTweets = new ArrayList<Tweet>();

            for(Tweet tweet : tweets){
                tweet.setTaggedText(classifier.classifyToString(TweetTreatment.treatTweet(tweet.getText()), "tsv", false));
                 if(tweet.getTaggedText().contains("B-LOCATION") || tweet.getTaggedText().contains("I-LOCATION")
                         || tweet.getTaggedText().contains("B-EVENT") || tweet.getTaggedText().contains("I-EVENT")) {
                    taggedTweets.add(tweet);
                 }
            }

            sw.stop();
            System.out.println("Classification done. Time: " + sw.toString());
            return taggedTweets;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tweet tagFromJSON(String json){

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<Tweet>(){}.getType();
            final Tweet tweet = gson.fromJson(json, listType);


            tweet.setTaggedText(classifier.classifyToString(TweetTreatment.treatTweet(tweet.getText()), "slashTags", false));

            // if((tweet.getTaggedText().contains("B-LOCATION") || tweet.getTaggedText().contains("I-LOCATION"))
            //         && (tweet.getTaggedText().contains("B-EVENT") || tweet.getTaggedText().contains("I-EVENT"))) {
                //TODO: Adicionar aqui a chammada dos métodos para banco de dados e geolicalização.
                CompletableFuture.runAsync(new Runnable(){
                    public void run() {
                        DBConnection.writeToDB(tweet);
                    }
                });
            // }

            return tweet;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void countFromJSON(String filepath){
        FileInputStream fstream;
        System.out.println("Classifying...");
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            fstream = new FileInputStream(filepath);
            InputStreamReader reader = new InputStreamReader(fstream);

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Tweet>>(){}.getType();
            ArrayList<Tweet> tweets = gson.fromJson(reader, listType);


            int count = 0;
            for(Tweet tweet : tweets){
                tweet.setTaggedText(classifier.classifyToString(TweetTreatment.treatTweet(tweet.getText()), "tsv", false));
                if(tweet.getTaggedText().contains("B-LOCATION") || tweet.getTaggedText().contains("I-LOCATION")
                        || tweet.getTaggedText().contains("B-EVENT") || tweet.getTaggedText().contains("I-EVENT")) {
                    count++;
                }
            }

            System.out.println("Count:" + count + " of " + tweets.size());
            sw.stop();
            System.out.println("Classification done. Time: " + sw.toString());
            return;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static void writeJsonTSV(ArrayList<Tweet> tweets){
        Gson gson = new Gson();
        String json = gson.toJson(tweets);

        if(tweets != null) {
            try {
                StopWatch sw = new StopWatch();
                System.out.println("Writing files...");
                sw.start();
                File file = new File("resources/experimentos_finais_3.json");
                PrintWriter outputJson = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                outputJson.write(json);
                outputJson.close();


                File fileTxt = new File("resources/experimentos_finais_3.tsv");
                FileWriter output = new FileWriter(fileTxt, true);
                for (Tweet tweet : tweets) {
                    tweet.setTaggedText(tweet.getTaggedText().concat("\n----------------\n"));
                    output.write(tweet.getTaggedText());
                }
                output.close();
                sw.stop();
                System.out.println("Done. Time: " + sw.toString());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            System.out.println("No data to write");
        }
    }

    public static ArrayList<Tweet> tagFromCSV(String filepath){
        FileInputStream fstream;
        ArrayList<Tweet> taggedTweets = new ArrayList<Tweet>();
        try{
            fstream = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            File model = new File("resources/model-experimento4.ser.gz");
            SeqClassifierFlags flags = new SeqClassifierFlags();
            CRFClassifier classifier = new CRFClassifier(flags);
            classifier.loadClassifier(model);
            classifier.flags.inputEncoding = "UTF-8";
            classifier.flags.outputEncoding = "UTF-8";


            String strLine;
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] linetokens = strLine.split("<>");
                if(linetokens.length >= 3) {
                    Tweet tweet = new Tweet();
                    tweet.setId(Long.parseLong(linetokens[0]));
                    tweet.setCreatedById(Long.parseLong(linetokens[1]));
                    tweet.setText(linetokens[2]);
                    tweet.setTaggedText(classifier.classifyToString(tweet.getText(), "tsv", false));
                    tweet.setCreatedAt(linetokens[3]);
                    if (linetokens.length == 6) {
                        tweet.setLatitude(Double.parseDouble(linetokens[4]));
                        tweet.setLongitude(Double.parseDouble(linetokens[5]));
                    }
                    System.out.println(tweet.getTaggedText());
                    taggedTweets.add(tweet);
                }
            }

            br.close();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return taggedTweets;
    }
}
