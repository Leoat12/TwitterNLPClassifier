package br.unirio;

import br.unirio.utils.*;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    //TODO: Instalar handler de parâmetros e menu
    public static void main( String[] args )
    {
        System.out.println("Application started.");

        DBConnection.writeToDB();

//        GeoLocation gl = new GeoLocation();
//        String locationTest = "Estação Jardim Oceânico";
//        gl.getLocationFromGoogle(locationTest);
//        gl.getLocationFromNominatim(locationTest);

//        countFromJSON("src/main/resources/tweets_5000001-6000000.json",
//                "src/main/resources/model-experimento8.ser.gz");

//        try {
//            TweetTagger tagger =  new TweetTagger("src/main/resources/model-experimento10.ser.gz");
//            ArrayList<Tweet> tweets = tagger.tagFromJSONFile("src/main/resources/TweetsCanalOficial.json");
//            tagger.writeJsonTSV(tweets);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    //     try {
    //         TweetReceiver.receiveTweet();
    //     }
    //     catch (Exception ex){
    //         ex.printStackTrace();
    //     }
     }
}
