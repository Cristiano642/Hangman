package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    public final static String PREFIX = "";

    private ServerSocket serverSocket;

    //This String will receive a word randomly from gameWords list.
    private String hangManWord;

    //List of words.
    private String[] gameWords = {
            "class",
            "method",
            "message",
            "question",
            "infinite",
            "academic",
            "informal",
            "festival",
            "benfica",
            "positive",
            "computer",
            "programming",
            "abstract",
            "properties",
            "collections",
            "override",
            "variable"
    };

    // Constructors
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }
    private int random = (int) (Math.random() * gameWords.length);

    // This HashMap will receive each connected player (name & socket)
    HashMap<String, Socket> playerList = new HashMap<>();

    // Method to get the players list
    public HashMap<String, Socket> getPlayerList() {
        return playerList;
    }
    private int timer = 45;
    public void init() {
        while (true) {

            // Server will save a random word and send it to all players
                hangManWord = gameWords[random];
            try {
                System.out.println("Waiting for player");
                Socket client = null;

                    client = serverSocket.accept();

                PrintStream printStream = new PrintStream(client.getOutputStream());

                Prompt prompt = new Prompt(client.getInputStream(), printStream);

                // FileReader for the game menu
                FileReader gameMenu = new FileReader("resources/HangMan_menu.txt");
                BufferedReader readMenu = new BufferedReader(gameMenu);

                String menu; //

                // Here the server will print the game menu with game title and rules
                while ((menu = readMenu.readLine()) != null) {
                    printStream.println(menu);
                    printStream.flush();
                }

                // This prompt will ask the players name and save it on "name" variable.
                StringInputScanner askName = new StringInputScanner();
                askName.setMessage("What is your name? \n");
                askName.setError("Write you name...");

                String name = prompt.getUserInput(askName); // player name

                printStream.println("\n Welcome to HangMan game " + name);

                // Here the server will save the player name as a key and his socket as the value at the HashMap playerList.
                playerList.put(name, client);

                System.out.println(name + " connected");

                /*while (timer != 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    timer--;
                    printStream.println(timer);
                }*/

                // Here the server will start the Thread and pass the player information (name,socket) & the word to the ServerHelper.
                    Thread thread = new Thread(new ServerHelper(client, name, hangManWord, this));
                    thread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        }
    }