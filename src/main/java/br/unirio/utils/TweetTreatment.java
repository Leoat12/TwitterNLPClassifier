package br.unirio.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;

public class TweetTreatment {
    public static String treatTweet(String text){
        return replaceAcronyms(removeUrl(text));
    }

    private static String removeUrl(String text)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        int i = 0;
        while (m.find()) {
            text = text.replaceAll(m.group(i),"").trim();
            i++;
        }
        return text;
    }

    /*  Pref. -> Pref
        Estr. -> Estrada
    */
    private static String replaceAcronyms (String text){
        String newText = text.replace("Av.", "Avenida");
        newText = newText.replace("AV.", "AVENIDA");
        newText = newText.replace("Est.", "Estrada");
        newText = newText.replace("Ger.", "Ger");
        newText = newText.replace("Elev.", "Elevado");
        newText = newText.replace("VD.", "Viaduto");
        return newText;
    }

    //TODO: Fazer casos de teste quando há mais de uma localização e um tweet.
    public static ArrayList<String> extractLocation(String taggedText){
        BufferedReader reader = new BufferedReader(new StringReader(taggedText));

        ArrayList<String> tagsLocation = new ArrayList<String>(Arrays.asList("B-LOCATION", "I-LOCATION"));
        ArrayList<String> locations = new ArrayList<String>();

        try{
            String line = null;
            String location = "";
            while((line = reader.readLine()) != null){
                if(!line.equals("")){
                    String[] tokens = line.split("\t");
                    if(tagsLocation.contains(tokens[1])){
                        location = location.concat(tokens[0] + " ");
                    }
                    else{
                        if(!location.equals("")){
                            location = location.trim();
                            locations.add(location);
                            location = "";
                        }
                    } 
                }   
            }

            if(!location.equals("")){
                location = location.trim();
                locations.add(location);
            }

            return locations;
        }
        catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    //TODO: Fazer casos de teste para extractEvent
    public static ArrayList<String> extractEvent(String taggedText){
        BufferedReader reader = new BufferedReader(new StringReader(taggedText));

        ArrayList<String> tagsEvent = new ArrayList<String>(Arrays.asList("B-EVENT", "I-EVENT"));
        ArrayList<String> events = new ArrayList<String>();

        try{
            String line = null;
            String event = "";
            while((line = reader.readLine()) != null){
                if(!line.equals("")){
                    String[] tokens = line.split("\t");
                    if(tagsEvent.contains(tokens[1])){
                        event = event.concat(tokens[0] + " ");
                    }
                    else{
                        if(!event.equals("")){
                            event = event.trim();
                            events.add(event);
                            event = "";
                        }
                    } 
                }   
            }

            if(!event.equals("")){
                event = event.trim();
                events.add(event);
            }

            return events;
        }
        catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
