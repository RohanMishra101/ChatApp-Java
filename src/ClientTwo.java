import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTwo{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String uname;
        System.out.println("Enter your name : ");
        uname = scanner.nextLine();


        System.out.println("======="+uname+"=======");
        try {
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
//                        printWriter.println(sendMsg);
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
