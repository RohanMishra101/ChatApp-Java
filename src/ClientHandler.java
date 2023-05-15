import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private ArrayList<Socket> clients;
    private String sendMsg, receiveMsg;
    private Socket clientSocket;



    public ClientHandler(Socket clientSocket, ArrayList<Socket> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            //Getting client msg
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //User/Server msg
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

            Runnable r1 = () -> {
                while (true) {
                    try {
                        receiveMsg = inputReader.readLine();
                        System.out.println(receiveMsg);
                    } catch (IOException e) {
//                        throw new RuntimeException(e);
                        System.out.println("Client Disconnected!!");

                    }finally {
                        try {
                            for (Socket socket:
                                 clients) {
//                                if(socket == null){
//                                    continue;
//                                }
                                if(socket != clientSocket){
                                    PrintWriter clientWriter = new PrintWriter(socket.getOutputStream());
                                    clientWriter.println(receiveMsg);
                                    clientWriter.flush();
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }



                }
            };

            Runnable r2 = () -> {
                while (true) {

                    printWriter.flush();
                    try {
                        sendMsg = userInputReader.readLine();
                        for (Socket socket : clients) {
//                            if(socket == null){
//                                continue;
//                            }
                            PrintWriter clientWriter = new PrintWriter(socket.getOutputStream());
                            clientWriter.println("Server: " + sendMsg);
                            clientWriter.flush();
                        }
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
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
