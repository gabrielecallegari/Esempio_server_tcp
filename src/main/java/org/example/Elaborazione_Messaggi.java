package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Elaborazione_Messaggi extends Thread{
    Socket clientSocket=null;
    ServerSocket serverSocket=null;
    //dichiaro e inizializzo la funzione fuori dal try catch perché sennò java potrebbe
    //darmi problemi perché nel caso vada nel catch la variabile non viene inizializzata


    Elaborazione_Messaggi(){} //costruttore vuoto
    Elaborazione_Messaggi(Socket clientSocket, ServerSocket serverSocket){
        this.clientSocket=clientSocket;
        this.serverSocket=serverSocket;
    }

    //parte thread
    @Override
    public void run(){
        BufferedReader input=null; //Dichiarazione del buffered reader che leggerà quello che invia il client
        try {
            input=new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //serve per leggere in input
        } catch (IOException e) {
            System.out.println("Errore nella creazione dell'input reader\n"+e.toString());
        }

        String message = "";
        PrintWriter output = null;
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true); //servirà per mandare il messaggio al client
        } catch (IOException e) {
            System.out.println("Errore nell'apertura output\nErrore:" + e.toString());
        }
        output.println("Connesso al server");
        try {
            while ((message = input.readLine()) != null) {
                output.println(message.toUpperCase(Locale.ROOT)); //restituisce al client quello che ha inviato in maiuscolo
            }

            System.out.println("Client disocnnesso");
        } catch (IOException e) {
            System.out.println("Errore in lettura\n"+e.toString());
        }
    }
}
