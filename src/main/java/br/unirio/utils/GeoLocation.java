package br.unirio.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.SimpleSearchQuery;
import fr.dudie.nominatim.model.Address;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class GeoLocation {
    //TODO: Reformar classe para ser utilizada tanto no modo manual quanto no modo automático
    public void getLocationFromGoogle(String location){
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyArf2JHHAbswIzCtb7aBpKzG0rRZmT4094")
                .build();
        try {
            PlacesSearchResponse response = PlacesApi.textSearchQuery(context, location).await();
            PlacesSearchResult[] results = response.results;

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(results);

            File file = new File("resources/google_maps_teste.json");
            PrintWriter outputJson = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            outputJson.write(json);
            outputJson.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getLocationFromNominatim(String location){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        SimpleSearchQuery query = new SimpleSearchQuery(location);
        NominatimSearchRequest request = new NominatimSearchRequest();
        request.setQuery(query);
        request.setViewBox(-43.761292, -23.076889, -43.091125, -22.742306);
        request.setBounded(true);
        JsonNominatimClient client = new JsonNominatimClient(httpClient, "leoat12@gmail.com");
        try {
            List<Address> results = client.search(request);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(results);

            File file = new File("resources/nominatim_teste.json");
            PrintWriter outputJson = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            outputJson.write(json);
            outputJson.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
