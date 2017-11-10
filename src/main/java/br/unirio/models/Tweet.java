package br.unirio.models;

import lombok.Data;

@Data
public class Tweet {
    private long Id;
    private long CreatedById;
    private String Text;
    private String taggedText;
    private String CreatedAt;
    private double Latitude;
    private double Longitude;
}
