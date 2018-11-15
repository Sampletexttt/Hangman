package nl.mack.hangman;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangMan {

    public static void main(String[] args) {


        new HangMan().rungame();
    }
    //De woorden waar de code uit kan kiezen, deze kan je eindeloos aan blijven vullen
    private String[] words = new String[]{"Lachen", "Monteur", "Klojo", "Computer", "Java", "Onderwijs", "Nederland", "Auto", "Vliegtuig", "Klaslokaal"};


    private boolean gameOver;
    //De hoeveelheid levens waarmee de speler begint
    private int lives = 6;

    // Hier slaan we het random woord op
    private String word;

    // Hier slaan we de geraden letters op
    private ArrayList<String> guessedCharacters;

    // Dit is de functie die alle code runt
    private void rungame() {
        //als gameover false is kan je door spelen
        gameOver = false;
        //de hoeveelheid levens waarmee de speler begint
        lives = 6;
        //de welkom message die bij het begin van het spel gegeven wordt met uitleg
        System.out.println("Welkom!");
        System.out.println("Instructies: type een letter in en druk vervolgens op ENTER");

        // Hier verkrijgen we een random getal afhankelijk van de grote van de array met mogelijke woorden
        int randomInt = new Random().nextInt(words.length);

        // Hier setten we de value van de guessedCharacters List naar een nieuwe (lege) ArrayList
        guessedCharacters = new ArrayList<>();

        // Hier slaan wij een random woord op door middel van het random getal van eerder uit de 'words' array
        word = words[randomInt];

        // Hier loopen we net zolang totdat de speler dood, heeft gewonnen of heeft geexit met het commando
        while (!gameOver) {

            //Deze code maakt een string builder zodat wij netjes een String kunnen maken met de al geraden letters
            StringBuilder stringBuilder = new StringBuilder();

            // Deze loop zorgt dat de geraden letters lijst netjes word omgezet in een String zodat we deze later kunnen printen
            for (int i = 0; i < guessedCharacters.size(); i++) {
                if (i == 0) {
                    stringBuilder.append(guessedCharacters.get(i));
                } else {
                    stringBuilder.append(", " +
                            "").append(guessedCharacters.get(i));
                }
            }

            // Hier printen we de zin met geraden letters die we hier boven hebben geschreven
            System.out.println("Geraden letters: " + (guessedCharacters.isEmpty() ? "Niks" : stringBuilder.toString()));

            // Hier printen we de value van displayWord() zie de functie om te kijken wat de value is
            System.out.println("Word: " + displayWord());

            // Hier maken wij een scanner voor player input
            Scanner scanner = new Scanner(System.in);

            // Hier slaan we de input van de player op
            String input = scanner.nextLine();

            // Is de input maar 1 letter groot
            if (input.length() == 1) {
                // Is char

                // Hebben we de letter (input van speler) al eerder opgegeven?
                if (guessedCharacters.contains(input.toLowerCase())) {
                    // Verstuur een bericht dat de input al eens in gebruikt
                    sendDuplicateMessage();
                } else {
                    // Voeg de nog niet geraden letter toe aan de lijst met al geraden letters
                    guessedCharacters.add(input.toLowerCase());

                    // Een custom contains functie die checked of de letter in de 'word' string zit (deze functie zorgt ervoor dat hij hoofdletters negeert)
                    if (containsIngoreCase(input)) {

                        // Verstuur een bericht dat de geraden letter (input van speler) in het woord zit
                        sendCorrectMessage();

                    } else {

                        // Als de geraden letter fout is (input van speler) halen we 1 leven van de speler af en versturen we een bericht dat het fout was en hoeveel levens hij nog heeft
                        damage();
                    }
                }

                // Als de input niet 1 letter groot is kijken we of hij gebruik heeft gemaakt van het exit commando
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bedankt voor het spelen!");

                // Hier breken we uit de loop omdat we niet meer willen spelen
                break;
            } else { // Als de input niet 1 letter is en ook niet het 'exit' commando is dan zeggen we dat de input niet correct is
                System.out.println("Je mag maar 1 letter invoeren!");
            }

            if (lives <= 0) { // Hier kijken we of de speler geen levens meer heeft

                // Hier zetten we de gameOver boolean op true, zodat de while loop stopt en het spel is afgelopen
                gameOver = true;

                // Stuur een bericht dat de speler het spel heeft verloren
                lose();
            }

            if (!displayWord().contains("_")) { // hier kijken we of het displayWord geen '_' meer bevat, hierdoor weten we dat alle letters geraden zijn
                // Hier zetten we de gameOver boolean op true, zodat de while loop stopt en het spel is afgelopen
                gameOver = true;

                // Stuur een bericht dat de speler het spel heeft gewonnen
                win();
            }
        }

        // Hier onthullen we het woord dat de speler moest raden (we printen het gekozen random woord)
        System.out.println("word: " + word);

        // Hier loopen we net zo lang totdat de speler ja / nee heeft getypt
        while (true) {
            System.out.println("Wil je het nog een keer proberen?");
            System.out.println("Ja/Nee");

            // Hier maken we een nieuwe scanner aan voor de speler input
            Scanner scanner = new Scanner(System.in);

            // Hier slaan we de speler input op
            String input = scanner.nextLine();

            // Kijkt of de input gelijk is aan 'ja' (we negeren hoofdletters)
            if (input.equalsIgnoreCase("Ja")) {

                // We activeren deze (runGame()) functie opnieuw om het spel nog een keer te starten
                rungame();

                // We breken uit de loop om de loop te beindigen
                break;
            } else if (input.equalsIgnoreCase("Nee")) { // Kijkt of de input gelijk is aan 'nee' (we negeren hoofdletters)
                System.out.println("Oké doei");

                // We breken uit de loop om de loop te beindigen
                break;
            } else { // Wanneer de input niet ja of nee is informeren we de speler dat het commando onjuist is / niet herkent word
                System.out.println("'" + input + "', is geen command.");
            }
        }
    }

    private void lose() {
        // Laten de speler weten dat hij heeft verloren
        System.out.println("");
    }

    private void win() {
        // Laten de speler weten dat hij heeft gewonnen
        System.out.println("Gefeliciteerd, je hebt gewonnen!");
    }

    private void sendCorrectMessage() {
        // Laten de speler weten dat zijn geraden letter juist is
        System.out.println("Juist!");
    }

    private void damage() {

        // Trekken 1 leven van de speler af
        lives -= 1;


        // Melden aan de speler dat de opgegeven letter onjuist is
        System.out.println("Dat is onjuist!");


        if (lives != 0) { // Checked of de levens van de speler NIET 0 is en vermelden dan gelijk hoeveel levens hij nog heeft
            System.out.println("Je hebt nog '" + lives + "' levens!");
        } else { // Wanneer de speler 0 levens heeft melden we hem dat die dood is
            System.out.println("Oeps, je bent dood.");
        }
    }

    private void sendDuplicateMessage() {
        // We melden de speler dat hij de opgegeven letter (input) al geraden heeft
        System.out.println("Je hebt deze letter al ingevoerd!");
    }


    private String displayWord() {
        // We maken een nieuwe stringbuilder aan
        StringBuilder stringBuilder = new StringBuilder();

        // We maken een loop die elke letter van het te raden woord af gaat
        for (int i = 0; i < word.length(); i++) {
            // We pakken de letter in het woord op plek 'i'
            char character = word.charAt(i);

            // We converten de char type naar een string zodat we gebruik kunnen maken van bepaalde functies
            String letter = Character.toString(character);

            // we checken of de letter al geraden is
            if (guessedCharacters.contains(letter.toLowerCase())) {

                // Als de letter al geraden is voegt hij deze letter toe aan de stringbuilder
                stringBuilder.append(letter);
            } else { // Als de letter nog niet geraden is dan voegt hij een '_' toe, aangezien de speler niet de te raden letters al mag zien
                stringBuilder.append("_");
            }
        }

        // We returnen de string die we net gebouwd hebben met de loop in de stringbuilder
        return stringBuilder.toString();
    }

    // Deze functie kijkt of de string 'word' een letter/string bevat en het maakt niet uit of dit een hoofdletter is of niet.
    private boolean containsIngoreCase(String character) {
        return word.contains(character.toLowerCase()) || word.contains(character.toUpperCase());
    }
}