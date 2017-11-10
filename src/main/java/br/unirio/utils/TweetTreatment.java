package br.unirio.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
