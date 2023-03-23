package com.example.final_project;
/*
Class - Purpose: Currency.java - A class written to convert currency in a given rate map (hashmap) to another inside of that hashmap
Author: Austin Formagin
Creation: 3/23/2023
*/

import java.util.HashMap;
import java.util.Map;

public class Currency {
    //Creation of a hashmap that we will refer to as a rate map
    private Map<String, Map<String, Double>> conversionRates = new HashMap<>();

    //Default Currency constructor
    public Currency() {

        /*
        The HashMap takes in FROM_CURRENCY and TO_CURRENCY as strings, and
        CONVERSION_RATE as a double. This double is representative of how many
        of the to the from is. In the example of Copper Piece to Gold Piece, a
        Copper Piece is 1/100th of a Gold Piece.
        */
        //Add default conversion rates
        //COPPER
        addConversionRate("cp", "cp", 1.0);
        addConversionRate("cp", "sp", 1.0 / 10);
        addConversionRate("cp", "gp", 1.0 / 100);
        addConversionRate("cp", "pp", 1.0 / 1000);
        //SILVER
        addConversionRate("sp", "cp", 10.0);
        addConversionRate("sp", "sp", 1.0);
        addConversionRate("sp", "gp", 1.0 / 10);
        addConversionRate("sp", "pp", 1.0 / 100);
        //GOLD
        addConversionRate("gp", "cp", 100.0);
        addConversionRate("gp", "sp", 10.0);
        addConversionRate("gp", "gp", 1.0);
        addConversionRate("gp", "pp", 1.0 / 10);
        //PLATINUM
        addConversionRate("pp", "cp", 1000.0);
        addConversionRate("pp", "sp", 100.0);
        addConversionRate("pp", "gp", 10.0);
        addConversionRate("pp", "pp", 1.0);
    }

    //Secondary Currency Constructor that takes in a conversion HashMap
    public Currency(HashMap<String, Map<String, Double>> conversion) {
        conversionRates = conversion; //Sets the conversion rate to the given rate map
    }

    public void addConversionRate(String fromCurrency, String toCurrency, double conversionRate) {
        if (!conversionRates.containsKey(fromCurrency)) {
            conversionRates.put(fromCurrency, new HashMap<>());
        }
        conversionRates.get(fromCurrency).put(toCurrency, conversionRate);
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        //Check to see if the conversions are in the map, otherwise throw an exception
        //TODO - Investigate the NPE that can be caused by containsKey and determine if that needs
        // an Object.NotNull added
        if (!conversionRates.containsKey(fromCurrency) || !conversionRates.get(fromCurrency).containsKey(toCurrency))
            throw new IllegalArgumentException("Invalid currency type: " + fromCurrency + " or " + toCurrency);

        double conversionRate;
       /*
        Compiler warning on the below line, but it should be fine. Due to the above check, this should
        not happen
        */
        conversionRate = conversionRates.get(fromCurrency).get(toCurrency);
        return amount * conversionRate; //Return the new value
    }

    public static void main(String[] args) {
        //FOR TESTING PURPOSES ONLY
        Currency c = new Currency();
        c.convertCurrency(100, "cp", "gp");

    }
}