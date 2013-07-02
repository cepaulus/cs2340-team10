package edu.gatech.cs2340.risk.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.CountryUtil;

//@RunWith(Junit4.class)
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

}
