package test.java.edu.gatech.cs2340.risk.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.CountryUtil;

@RunWith(JUnit4.class)

public class ArmyCalculationTest {

    @Test
    public void testNorthAmerica() {
        Player player = new Player(1, "NorthAmericaOwner");
        Territory alaska = new Territory(1, "Alaska");
	Territory alberta = new Territory(2, "Alberta");
	Territory centralAmerica = new Territory(3, "Central America");
        Territory easternUS = new Territory(4, "Eastern United States");
	Territory greenland = new Territory(5, "Greenland");
	Territory northwestTerritory = new Territory(6, "Northwest Territory");
	Territory ontario = new Territory(7, "Ontario");
        Territory quebec = new Territory(8, "Quebec");
        Territory westernUS = new Territory(9, "Western United States");
        player.addTerritory(alaska);
	player.addTerritory(alberta);
        player.addTerritory(centralAmerica);
        player.addTerritory(easternUS);
        player.addTerritory(greenland);
        player.addTerritory(northwestTerritory);
        player.addTerritory(ontario);
        player.addTerritory(quebec);
        player.addTerritory(westernUS);
        int points = CountryUtil.getPointsForCountries(player);
        assertEquals("Testing North America", 5, points);
    }

    @Test
    public void testSouthAmerica() {
        Player player = new Player(1, "SouthAmericaOwner)");
        Territory argentina = new Territory(10, "Argentina");
        Territory brazil = new Territory(11, "Brazil");
        Territory peru = new Territory(12, "Peru");
        Territory venezuela = new Territory(13, "Venezuela");
        player.addTerritory(argentina);
        player.addTerritory(brazil);
        player.addTerritory(peru);
        player.addTerritory(venezuela);
        int points = CountryUtil.getPointsForCountries(player);
        assertEquals("South America calculation failed.", 2, points);
    }

    @Test
    public void testAfrica() {
        Player player = new Player(1, "AfricaOwner");
        Territory congo = new Territory(14, "Congo");
        Territory eastAfrica = new Territory(15, "East Africa");
        Territory egypt = new Territory(16, "Egypt");
        Territory madagascar = new Territory(17, "Madagascar");
        Territory northAfrica = new Territory(18, "North Africa");
        Territory southAfrica = new Territory(19, "South Africa");
        player.addTerritory(congo);
        player.addTerritory(eastAfrica);
        player.addTerritory(egypt);
        player.addTerritory(madagascar);
        player.addTerritory(northAfrica);
        player.addTerritory(southAfrica);
        int points = CountryUtil.getPointsForCountries(player);
        assertEquals("Africa calculation failed.", 3, points);
    }

    @Test
    public void testMultipleCountries() {
        Player player = new Player(1, "AfricaAndSouthAmOwner");
        Territory congo = new Territory(14, "Congo");
        Territory eastAfrica = new Territory(15, "East Africa");
        Territory egypt = new Territory(16, "Egypt");
        Territory madagascar = new Territory(17, "Madagascar");
        Territory northAfrica = new Territory(18, "North Africa");
        Territory southAfrica = new Territory(19, "South Africa");
        Territory argentina = new Territory(10, "Argentina");
        Territory brazil = new Territory(11, "Brazil");
        Territory peru = new Territory(12, "Peru");
        Territory venezuela = new Territory(13, "Venezuela");
        player.addTerritory(argentina);
        player.addTerritory(brazil);
        player.addTerritory(peru);
        player.addTerritory(venezuela);
        player.addTerritory(congo);
        player.addTerritory(eastAfrica);
        player.addTerritory(egypt);
        player.addTerritory(madagascar);
        player.addTerritory(northAfrica);
        player.addTerritory(southAfrica);
        int points = CountryUtil.getPointsForCountries(player);
        assertEquals("Adding armies for multiple countries failed.", 5, points); 
    }


}
