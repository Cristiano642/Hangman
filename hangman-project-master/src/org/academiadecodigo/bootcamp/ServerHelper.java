package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;

public class ServerHelper implements Runnable {
    public final static String PREFIX = "";
    private Socket client;
    private Server server;
    private String name;
    private String hangManWord;
    private String[] wordSplit;
    private String[] finalArr;
    private int playerAttempts = 0;
    private int totalAttempts = 7;
    private int correctLetter = 0;
    boolean containsLetter = false;

    // ServerHelper Constructor
    public ServerHelper(Socket client, String name, String hangManWord, Server server) {
        this.client = client;
        this.server = server;
        this.name = name;
        this.hangManWord = hangManWord;
    }

    //This method will populate the Array with " _ "
    public void popfinalArr() {
        Arrays.fill(finalArr, " _ ");
    }

    // This method will check if the players letter exist. If it does, will return true.
    public boolean checkLetter(String letter) {
        for (int i = 0; i < wordSplit.length; i++) {
            if (letter.equals(wordSplit[i])) {
                containsLetter = true;
            }
        }
        return containsLetter;
    }

    // This method (for i loop) will iterate until he finds a " _ "
    // After that, will check if the letter played is equal to the game letter in the same position (i)
    public void addLetter(String letter) {
        for (int i = 0; i < wordSplit.length; i++) {
            if (finalArr[i].equals(" _ ")) {
                if (wordSplit[i].equals(letter)) {
                    finalArr[i] = " " + letter + " ";
                    correctLetter++;
                }
            }
        }
    }

    @Override
    public void run() {

        // Split the game word (given by the server)
        wordSplit = hangManWord.split("");

        // Here will create a new String and populate it with " _ "
        finalArr = new String[wordSplit.length];
        popfinalArr();

        while (playerAttempts < 7) {
            try {

                PrintStream localPrintStream = new PrintStream(client.getOutputStream());
                BufferedReader localReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                FileReader hanMan1 = new FileReader("resources/HangMan_1.txt");
                FileReader hanMan2 = new FileReader("resources/HangMan_2.txt");
                FileReader hanMan3 = new FileReader("resources/HangMan_3.txt");
                FileReader hanMan4 = new FileReader("resources/HangMan_4.txt");
                FileReader hanMan5 = new FileReader("resources/HangMan_5.txt");
                FileReader hanMan6 = new FileReader("resources/HangMan_6.txt");
                FileReader hanMan7 = new FileReader("resources/HangMan_7.txt");


                Prompt prompt = new Prompt(client.getInputStream(), localPrintStream);

                StringInputScanner askLetter = new StringInputScanner();

                // Print all the " _ "
                for (String l : finalArr)
                    localPrintStream.print(l);
                    localPrintStream.flush();

                //  Ask for a letter
                askLetter.setMessage("\n Guess a letter " + "(" + finalArr.length + " letters word) \n");
                askLetter.setError("Not a letter...");

                String letter = prompt.getUserInput(askLetter); // save the player letter

                if (checkLetter(letter) == true) {
                    addLetter(letter);
                    containsLetter = false;

                    // If guess all letters, send a msg to all players telling who won and close the printWriter
                    if (correctLetter == finalArr.length) {
                        for (String key : server.getPlayerList().keySet()) {

                            Socket localSocket = server.getPlayerList().get(key);
                            BufferedWriter localWriter = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream()));
                            localWriter.write(name + " WON " + "the word was " + hangManWord + "\n");
                            localWriter.flush();
                            localWriter.close();

                        }
                    }

                    // If the player give incorrect letter, will decrease the player attempts and print HangMan image
                } else {
                    playerAttempts++; // increase the player failed attempts
                    totalAttempts--; // decrease the total attempts

                    if (playerAttempts == 1) {
                        BufferedReader hangmanRead1 = new BufferedReader(hanMan1);

                        String hangman1;
                        while ((hangman1 = hangmanRead1.readLine()) != null) {
                            localPrintStream.print(hangman1 + "\n");
                            localPrintStream.flush();
                        }
                        localPrintStream.println("Try again " + "(you have " + totalAttempts + " attempts) \n");
                    } else if (playerAttempts == 2) {
                        BufferedReader hangmanRead2 = new BufferedReader(hanMan2);

                        String hangman2;
                        while ((hangman2 = hangmanRead2.readLine()) != null) {
                            localPrintStream.print(hangman2 + "\n");
                            localPrintStream.flush();
                        }
                        localPrintStream.println("Try again " + "(you have " + totalAttempts + " attempts) \n");
                    } else if (playerAttempts == 3) {
                        BufferedReader hangmanRead3 = new BufferedReader(hanMan3);

                        String hangman3;
                        while ((hangman3 = hangmanRead3.readLine()) != null) {
                            localPrintStream.print(hangman3 + "\n");
                            localPrintStream.flush();
                        }
                        localPrintStream.println("Try again " + "(you have " + totalAttempts + " attempts) \n");
                    } else if (playerAttempts == 4) {
                        BufferedReader hangmanRead4 = new BufferedReader(hanMan4);

                        String hangman4;
                        while ((hangman4 = hangmanRead4.readLine()) != null) {
                            localPrintStream.print(hangman4 + "\n");
                            localPrintStream.flush();
                        }
                        localPrintStream.println("Try again " + "(you have " + totalAttempts + " attempts) \n");
                    } else if (playerAttempts == 5) {
                        BufferedReader hangmanRead5 = new BufferedReader(hanMan5);

                        String hangman5;
                        while ((hangman5 = hangmanRead5.readLine()) != null) {
                            localPrintStream.print(hangman5 + "\n");
                            localPrintStream.flush();
                        }
                        localPrintStream.println("Try again " + "(you have " + totalAttempts + " attempts) \n");
                    } else if (playerAttempts == 6) {
                        BufferedReader hangmanRead6 = new BufferedReader(hanMan6);

                        String hangman6;
                        while ((hangman6 = hangmanRead6.readLine()) != null) {
                            localPrintStream.print(hangman6 + "\n");
                            localPrintStream.flush();
                        }
                        localPrintStream.println("Try again " + "(you have " + totalAttempts + " attempt) \n");
                    } else if (playerAttempts == 7) {
                        BufferedReader hangmanRead7 = new BufferedReader(hanMan7);

                        String hangman7;
                        while ((hangman7 = hangmanRead7.readLine()) != null) {
                            localPrintStream.print(hangman7 + "\n");
                            localPrintStream.flush();
                        }

                        localPrintStream.println("GAME OVER \n");
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}