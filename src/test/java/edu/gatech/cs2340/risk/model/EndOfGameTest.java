package test.java.edu.gatech.cs2340.risk.model;

import org.junit.Test;

import main.java.edu.gatech.cs2340.risk.model.*;

//@RunWith(Junit4.class)
public class EndOfGameTest {

    @Test
    public void testGameWon() {
        Player player = new Player(1, "GameWinner");
        Territory alaska = new Territory(1, "Alaska");
        Territory alberta = new Territory(2, "Alberta");
        Territory centralAmerica = new Territory(3, "Central America");
        Territory easternUS = new Territory(4, "Eastern United States");
        Territory greenland = new Territory(5, "Greenland");
        Territory northwestTerritory = new Territory(6, "Northwest Territory");
        Territory ontario = new Territory(7, "Ontario");
        Territory quebec = new Territory(8, "Quebec");
        Territory westernUS = new Territory(9, "Western United States");
        Territory argentina = new Territory(10, "Argentina");
        Territory brazil = new Territory(11, "Brazil");
        Territory peru = new Territory(12, "Peru");
        Territory venezuela = new Territory(13, "Venezuela");
        Territory congo = new Territory(14, "Congo");
        Territory eastAfrica = new Territory(15, "East Africa");
        Territory egypt = new Territory(16, "Egypt");
        Territory madagascar = new Territory(17, "Madagascar");
        Territory northAfrica = new Territory(18, "North Africa");
        Territory southAfrica = new Territory(19, "South Africa");
        Territory greatBritain = new Territory(20, "Great Britain");
        Territory iceland = new Territory(21, "Iceland");
        Territory northernEurope = new Territory(22, "Nothern Europe");
        Territory scandinavia = new Territory(23, "Scandinavia");
        Territory southernEurope = new Territory(24, "Southern Europe");
        Territory ukraine = new Territory(25, "Ukraine");
        Territory westernEurope = new Territory(26, "Western Europe");
        Territory afghanistan = new Territory(27, "Afghanistan");
        Territory china = new Territory(28, "China");
        Territory india = new Territory(29, "India");
        Territory irkutsk = new Territory(30, "Irkutsk");
        Territory japan = new Territory(31, "Japan");
        Territory kamchatka = new Territory(32, "Kamchatka");
        Territory middleEast = new Territory(33, "Middle East");
        Territory mongolia = new Territory(34, "Mongolia");
        Territory siam = new Territory(35, "Siam");
        Territory siberia = new Territory(36, "Siberia");
        Territory ural = new Territory(37, "Ural");
        Territory yakutsk = new Territory(38, "Yakutsk");
        Territory easternAus = new Territory(39, "Eastern Australia");
        Territory indonesia = new Territory(40, "Indonesia");
        Territory newGuinea = new Territory(41, "New Guinea");
        Territory westernAus = new Territory(42, "Western Australia");
        player.addTerritory(alaska);
        player.addTerritory(alberta);
        player.addTerritory(centralAmerica);
        player.addTerritory(easternUS);
        player.addTerritory(greenland);
        player.addTerritory(northwestTerritory);
        player.addTerritory(ontario);
        player.addTerritory(quebec);
        player.addTerritory(westernUS);
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
        player.addTerritory(greatBritain);
        player.addTerritory(iceland);
        player.addTerritory(northernEurope);
        player.addTerritory(scandinavia);
        player.addTerritory(southernEurope);
        player.addTerritory(ukraine);
        player.addTerritory(westernEurope);
        player.addTerritory(afghanistan);
        player.addTerritory(china);
        player.addTerritory(india);
        player.addTerritory(irkutsk);
        player.addTerritory(japan);
        player.addTerritory(kamchatka);
        player.addTerritory(middleEast);
        player.addTerritory(mongolia);
        player.addTerritory(siam);
        player.addTerritory(siberia);
        player.addTerritory(ural);
        player.addTerritory(yakutsk);
        player.addTerritory(easternAus);
        player.addTerritory(indonesia);
        player.addTerritory(newGuinea);
        player.addTerritory(westernAus); 
        //TODO check if player is winner
    }
}
