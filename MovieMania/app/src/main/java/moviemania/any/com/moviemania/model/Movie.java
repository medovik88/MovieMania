package moviemania.any.com.moviemania.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ASTER-NOTUS on 09.12.2015.
 */
public class Movie
{
    private String id;
    private String name;
    private ArrayList<String> genres;
    public  final static ArrayList<String> ALLGENRES= new ArrayList<String>();
    private Rate rate;
    private float averageRate;
    private float generalRate;

    public Movie(String name) {
        this.name = name;
        genres = new ArrayList<String>();
        id = UUID.randomUUID().toString() + this.hashCode();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public static ArrayList<String> getAllGenres() {
        return ALLGENRES;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public float getGeneralRate() {
        return generalRate;
    }

    public void setGeneralRate(float generalRate) {
        this.generalRate = generalRate;
    }

    public float getAverageRate() {
        return averageRate;
    }




    //This method should be called after filling all rates.
    public float calculateAverageRate(Rate rate){
        if(rate != null){
        averageRate = (float)rate.getSumOfAllRates()/6; }


        return averageRate;
    }

    public static void fillGenres(){
        ALLGENRES.add(0, "Action");
        ALLGENRES.add(1, "Adventure");
        ALLGENRES.add(2, "Biography");
        ALLGENRES.add(3, "Cartoon");
        ALLGENRES.add(4, "Comedy");
        ALLGENRES.add(5, "Crime");
        ALLGENRES.add(6, "Disaster");
        ALLGENRES.add(7, "Documental");
        ALLGENRES.add(8, "Fantasy");
        ALLGENRES.add(9, "Horror");
        ALLGENRES.add(10, "Musical");
        ALLGENRES.add(11, "Mystery");
        ALLGENRES.add(12, "Romance");
        ALLGENRES.add(13, "Scientific");
        ALLGENRES.add(14, "Sci-fi");
        ALLGENRES.add(15, "Short Film");
        ALLGENRES.add(16, "Sitcom");
        ALLGENRES.add(17, "Soap Opera");
        ALLGENRES.add(18, "Sports");
        ALLGENRES.add(19, "Western");
    }

    public void addGenresToMovie(int number){
        genres.add(ALLGENRES.get(number));
    }

}
