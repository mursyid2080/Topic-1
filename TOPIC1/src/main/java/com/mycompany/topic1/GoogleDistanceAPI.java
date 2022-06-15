/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.topic1;

import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GoogleDistanceAPI {
    private static final String API_KEY = "AIzaSyApU0_BL0WUids6ocsdYoxmcPZsm1M-CaA";
    public static float[][] distance;
    public static float[][] times;

    public static long[] getData(String source, String destination)throws Exception{
        //request distance matrix
        var url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source + "&destinations=" + destination + "&key=" + API_KEY;
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        JSONParser jp = new JSONParser();
        //get distance and time from the json file
        JSONObject jo = (JSONObject) jp.parse(response); //contains all information in the json file
        JSONArray ja = (JSONArray) jo.get("rows");  //get information from rows which contain the distance and duration
        jo = (JSONObject) ja.get(0);    //get contains information from elements only
        ja = (JSONArray) jo.get("elements");    //store information inside elements
        jo = (JSONObject) ja.get(0);    //store the new JSONArray
        JSONObject jdistance = (JSONObject) jo.get("distance");
        JSONObject jduration = (JSONObject) jo.get("duration");
        long distance = (long) jdistance.get("value");  //get value of distance in meter
        long duration = (long) jduration.get("value");  //get value of duration in seconds

        long[] value = {distance, duration};

        return value;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Scanner s = new Scanner(System.in);
        System.out.print("Current Location : ");
        String source = s.nextLine();
        System.out.print("Destination : ");
        String destination = s.nextLine();

        String newsource = source.replace(" ", "");
        String newdestination = destination.replace(" ", "");

        long[] obj = getData(newsource,newdestination);

        Double distance = (double)(long) obj[0];    //convert long to double
        Integer duration = (int)(long) obj[1];  //convert long to integer
        int minutes = (duration / 60)%60;
        int hours = (duration / 60)/60;
        System.out.printf("%.2f km", (distance/1000));
        System.out.println("");
        if(duration > 3600)
            System.out.print(hours + " hours " + minutes + " minutes");
        else
            System.out.println(minutes + "min");
    }

}