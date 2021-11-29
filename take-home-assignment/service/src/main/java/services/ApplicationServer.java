package services;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8081)
                .addService(new LoginServiceImpl())
                .build()
                .start();
        log.info("gRPC Server Started");
        server.awaitTermination();
    }

}