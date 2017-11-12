package br.unirio.models;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

@Data
public class ToolProperties{
    private static ToolProperties instance = null;
    //TODO: Adicionar propriedades como community string do banco de dados e opção de GeoLocation
    private String modelpath;
    private String queueName;
    private int consumerTime;
    private String dbConnectionString;

    protected ToolProperties(){};

    public static ToolProperties getInstance(){
        if(instance == null){
            instance = new ToolProperties();
        }

        return instance;
    }

    public static boolean prepareProperties(String filepath){
        try{
            Gson gson = new Gson();
            File file = new File(filepath);
            FileReader reader = new FileReader(file);

            Type propsType = new TypeToken<ToolProperties>(){}.getType();
            ToolProperties auxProps = gson.fromJson(reader, propsType);

            ToolProperties props = ToolProperties.getInstance();

            props.setModelpath(auxProps.getModelpath());
            props.setDbConnectionString(auxProps.getDbConnectionString());
            props.setConsumerTime(auxProps.getConsumerTime());
            props.setQueueName(auxProps.getQueueName());

            return true;
        }
        catch(FileNotFoundException ex){
            System.out.println("Arquivo não encontrado. Verifique o caminho do arquivo.");
            return false;
        }
    }
}