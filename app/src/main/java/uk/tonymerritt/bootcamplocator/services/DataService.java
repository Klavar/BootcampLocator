package uk.tonymerritt.bootcamplocator.services;

import java.util.ArrayList;

import uk.tonymerritt.bootcamplocator.model.Klavar;

/**
 * Created by Klavar on 04/05/2017.
 */

public class DataService {
    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {
    }

    public ArrayList<Klavar> getBootcampLocationsWithin10MilesOfPostcode(int postcode) {
// pretending we are downloading data from the server

        ArrayList<Klavar> list = new ArrayList<>();
        list.add(new Klavar(50.808162f, -1.076579f, "New Road office", "21 New Road, Portsmouth England","klavarimage"));

        return list;

    }

}
