package br.unirio;

import br.unirio.models.ToolProperties;
import br.unirio.utils.*;


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

        if(args.length > 0){
            if(args[0].equals("-p")){
                if(args.length > 1){
                    if(ToolProperties.prepareProperties(args[1])){
                        try {
                            TweetReceiver.receiveTweet();
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }   
            }
        }
//        GeoLocation gl = new GeoLocation();
//        String locationTest = "Estação Jardim Oceânico";
//        gl.getLocationFromGoogle(locationTest);
//        gl.getLocationFromNominatim(locationTest);
        
     }
}
