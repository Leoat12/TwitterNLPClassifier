package br.unirio.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import br.unirio.models.ToolProperties;
import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.SimpleSearchQuery;
import fr.dudie.nominatim.model.Address;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

public class GeoLocation {
    public static LatLng getLocationFromGoogle(String location){
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(ToolProperties.getInstance().getGoogleAPIKey())
                .build();
        try {

            PlacesSearchResponse response = PlacesApi.textSearchQuery(context, location).language("pt-BR").await();
            PlacesSearchResult[] results = response.results;


            if(results.length == 0){
                return null;
            }
            else{
                return results[0].geometry.location;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static LatLng getLocationFromNominatim(String location){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        SimpleSearchQuery query = new SimpleSearchQuery(location);
        NominatimSearchRequest request = new NominatimSearchRequest();
        request.setQuery(query);
        request.setViewBox(-43.761292, -23.076889, -43.091125, -22.742306);
        request.setBounded(true);
        JsonNominatimClient client = new JsonNominatimClient(httpClient, ToolProperties.getInstance().getNominatimEmail());
        try {
            List<Address> results = client.search(request);
            
            if(results.isEmpty()){
                return null;
            }
            else{
                return new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
