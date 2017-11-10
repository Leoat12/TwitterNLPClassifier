package br.unirio.models;

import lombok.Data;

@Data
public class ToolProperties{
    private static ToolProperties instance = null;
    //TODO: Adicionar propriedades como community string do banco de dados e opção de GeoLocation
    private String modelpath;

    protected ToolProperties(){};

    public static ToolProperties getInstance(){
        if(instance == null){
            instance = new ToolProperties();
        }

        return instance;
    }
}