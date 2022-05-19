package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    public Server(){} //costruttore vuoto, si usa per inizializzare la classe senza passare parametri
    public Server(int numeroPorta){
        ServerSocket serverSocket=null; //dichiaro e inizializzo la funzione fuori dal try catch perché sennò java potrebbe
                                        //darmi problemi perché nel caso vada nel catch la variabile non viene inizializzata
        try {
            serverSocket=new ServerSocket(numeroPorta); //creo il server socket sulla porta passata nel costruttore
        } catch (IOException e) {
            System.out.println("Eccezione nella creazione del socket server\n"+e.toString());
        }
        Socket clientSocket=null; //stessa cosa del server socket

        while (true){
            try {
                clientSocket=serverSocket.accept(); //server prova ad accettare la connessione col client
                System.out.println("Connessione al client");
            } catch (IOException e) {
                //problemi nella connessione
                System.out.println("Errore nella accettazione da parte del server"+e.toString());
                break; //interrompo il ciclo per non far distruggere il programma N.B: col break butto in down il server
            }
            System.out.println("Connesso al client");
            Elaborazione_Messaggi tread=new Elaborazione_Messaggi(clientSocket,serverSocket);
            tread.start(); //partenza del thread
        }

        try {
            serverSocket.close(); //chiusura del socket
        } catch (IOException e) {
            System.out.println("Errore nella chiusura del socket");
        }

    }


}
