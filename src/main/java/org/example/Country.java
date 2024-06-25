package org.example;

public class Country {
    private String name;
    private String capital;
    private int population;
    private int area;

    public Country(String name, String capital, int population, int area) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }

    public int getArea() {
        return area;
    }

    @Override
    public String toString() {
        return name + ", " + capital + ", " + population + ", " + area;
    }
}
