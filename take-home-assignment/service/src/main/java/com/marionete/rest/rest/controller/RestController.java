package com.marionete.rest.rest.controller;


import com.marionete.rest.rest.service.RequestParser;
import com.marionete.rest.rest.service.UserAccountRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;
import services.UserAccount;

import javax.annotation.PostConstruct;

@Slf4j
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Value("${grpc.server.name}")
    private String grpcServer;

    @Value("${grpc.server.port}")
    int grpcPort;

    private UserAccountRepository repo;

    private ManagedChannel managedChannel;
    private LoginServiceGrpc.LoginServiceBlockingStub loginServiceBlockingStub;

    // This method is used for initialising UserAccountRepository, gRPC Channel and Stubs.
    @PostConstruct
    private void postConstruct() {
        repo = new UserAccountRepository();

        managedChannel = ManagedChannelBuilder.forAddress(grpcServer, grpcPort)
                .usePlaintext()
                .build();

        loginServiceBlockingStub = LoginServiceGrpc.newBlockingStub(managedChannel);
    }

    @PostMapping(path = "/marionete/useraccount/", consumes = "application/json", produces = "application/json")
    public ResponseEntity  userAccount(@RequestBody String request) {
        log.info("Received Request: "+request);

        ResponseEntity responseEntity = null;

        String[] params = RequestParser.getUserNameAndPassword(request);

        if (params == null) {
            responseEntity = new ResponseEntity("Invalid Request",HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        String username = params[0];
        String password = params[1];

        UserAccount account = repo.getUserAccount(username);

        //If the username is not present in the repository,  then this service returns Invalid Username
        if (account == null) {
            responseEntity = new ResponseEntity("Invalid Username",HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        //Create the LoginRequest
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        //Make Grpc call with the Request
        LoginResponse loginResponse = loginServiceBlockingStub.login(loginRequest);

        String token = loginResponse.getToken();

        // This is just for testing purpose. In real world scenario, this will not be logged.
        log.info("Token: "+token);

        if (token != null) {
            repo.setToken(username, token);
            responseEntity = new ResponseEntity(account, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity("Invalid Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
