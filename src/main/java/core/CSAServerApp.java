package core;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSAServerApp {
    private static Logger log = Logger.getLogger(CSAServerApp.class);

    public static void main(String[] args) {
        //Taking port number and log level as argument
        if (args.length != 2) {
            log.error("Illegal argument/s");
            return;
        } else {
            log.info("Initializing TCP server on port: " + args[0] + " at log level:" + args[1]);
        }

        //creating a pool of 10 threads
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        //creating server listener socket
        try {
            ServerSocket listener = new ServerSocket(Integer.parseInt(args[0]));
            log.info("TCP server running at host: "
                    + listener.getInetAddress().getHostAddress()
                    + " port:" + args[0]);

            //infinite loop to always listen for client until app is terminated
            while (1 == 1) {
                Socket client = listener.accept();

                //logging connected client info
                log.info("New connection from: " + client.getInetAddress().getHostAddress());

                //new thread from pool to serve connected client
                CSAServerWorker serverWorker = new CSAServerWorker(client);
                threadPool.execute(serverWorker);
            }

        } catch (IOException e) {
            threadPool.shutdown();
            log.error(e.getMessage());
            return;
        }
    }
}
