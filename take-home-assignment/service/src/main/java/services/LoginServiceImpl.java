package services;

public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {

    long token;

    @Override
    public void login(services.LoginRequest request,
                      io.grpc.stub.StreamObserver<services.LoginResponse> responseObserver) {
        token++;

        LoginResponse response = LoginResponse.newBuilder()
                .setToken("Token-"+token)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
