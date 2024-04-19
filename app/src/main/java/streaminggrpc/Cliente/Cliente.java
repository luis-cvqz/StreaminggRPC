package streaminggrpc.Cliente;

import com.proto.archivo.ArchivoServiceGrpc;
import com.proto.archivo.Streamarchivo.ArchivoRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 8080;

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, puerto).usePlaintext().build();

        streamArchivo(channel);

        System.out.println("Fin de archivo.");
        System.out.println("Apagando");
        channel.shutdown();
    }

    private static void streamArchivo(ManagedChannel ch) {
        ArchivoServiceGrpc.ArchivoServiceBlockingStub stub = ArchivoServiceGrpc.newBlockingStub(ch);
        ArchivoRequest archivoRequest = ArchivoRequest.newBuilder().setNombre("archivote.csv").build();

        stub.archivoStream(archivoRequest).forEachRemaining(archivoResponse -> {
            System.out.println(archivoResponse.getResultado());
        });
    }
}