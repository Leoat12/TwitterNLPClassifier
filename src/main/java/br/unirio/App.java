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
                            if(args.length > 3){
                                if(args[2].equals("-f")){
                                    TweetTagger tt = new TweetTagger(ToolProperties.getInstance().getModelpath());
                                    if(tt.tagFromJSONFile(args[3])){
                                        System.out.println("File succesfully tagged.");
                                    }
                                    else{
                                        System.out.println("Error found, make sure the file exists.");
                                    }
                                }
                            }
                            else{
                                TweetReceiver.receiveTweet();
                            }
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                }   
            }
        }
     }
}
