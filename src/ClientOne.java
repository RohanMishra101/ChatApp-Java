import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientOne {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String uname;
        System.out.println("Enter your name : ");
        uname = scanner.nextLine();


        System.out.println("======="+uname+"=======");
        try {
//            Socket socket = new Socket("172.25.20.173",12345);
            Socket socket = new Socket("localhost",12345);

            System.out.println("Connection Established!!");

            BufferedReader inputReader = new
                    BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            //User/Server msg
            BufferedReader userInputReader = new
                    BufferedReader(new InputStreamReader(System.in));

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());


            Runnable r1=  () -> {
                while(true){
                    try {
                        String receiveMsg = inputReader.readLine();
                        System.out.println(receiveMsg);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            Runnable r2 =  () -> {
                while(true){
                    try {
                        String sendMsg = userInputReader.readLine();
                        printWriter.println(uname +" : "+ sendMsg);
                        printWriter.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            Thread thread1 = new Thread(r1);
            Thread thread2 = new Thread(r2);

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

    // Start threads to read and write messages
//    Thread readThread = new Thread(() -> {
//        while (true) {
//            try {
//                String message = inputReader.readLine();
//                if (message == null) {
//                    break; // Server has disconnected
//                }
//                System.out.println("Server: " + message);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    });
//    Thread writeThread = new Thread(() -> {
//        while (true) {
//            try {
//                String message = userInputReader.readLine();
//                if (message == null) {
//                    break; // User has quit
//                }
//                printWriter.println(message);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    });
//
//// Start the threads
//            readThread.start();
//                    writeThread.start();
//
//                    // Wait for threads to finish
//                    readThread.join();
//                    writeThread.join();
//import java.io.*;
//import java.net.Socket;
//
//public class Client extends Thread{
//    static PrintWriter writer;
//    static String sendingmessage;
//    static String receivingmessage;
//    static BufferedReader bufferedReader;
//    static BufferedReader userinputreader;
//
//    public static void main(String[] args){
//
//        try {
//            Socket socket = new Socket("localhost",12345);
//            System.out.println("connection estabished");
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            userinputreader = new BufferedReader(new InputStreamReader(System.in));
//            OutputStream outputStream=socket.getOutputStream();
//            writer = new PrintWriter(outputStream);
//            // writer= new PrintWriter(outputStream);
//            Runnable r1=  () ->
//            {
//                while(true){
//                    try {
//                        receivingmessage=bufferedReader.readLine();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println(receivingmessage);
//                }
//
//            };
//            Runnable r2 =  () ->
//            {
//                while (true){
//                    try {
//                        sendingmessage=userinputreader.readLine();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    writer.println(sendingmessage);
//                    writer.flush();
//                }
//
//            };
//            Thread thread1 = new Thread(r1);
//            Thread thread2 = new Thread(r2);
//
//
//            thread1.start();
//            thread2.start();
//
//            thread1.join();
//            thread2.join();
//        } catch (IOException e) {
//            System.out.println("connection error form client");
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//}