package streaminggrpc.Servidor;

import com.proto.archivo.ArchivoServiceGrpc;
import com.proto.archivo.Streamarchivo.ArchivoRequest;
import com.proto.archivo.Streamarchivo.ArchivoResponse;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class ServidorImpl extends ArchivoServiceGrpc.ArchivoServiceImplBase {
    @Override
    public void archivoStream (ArchivoRequest archivoRequest, StreamObserver<ArchivoResponse> responseStreamObserver) {
        String archivoNombre = "/" + archivoRequest.getNombre();
        System.out.println("Enviando el archivo" + ServidorImpl.class.getResource(archivoNombre) + "...");

        try (Scanner scanner = new Scanner(ServidorImpl.class.getResourceAsStream(archivoNombre))) {
            while (scanner.hasNextLine()) {
                ArchivoResponse archivoResponse = ArchivoResponse.newBuilder().setResultado(scanner.nextLine()).build();
                //System.out.println(".");
                responseStreamObserver.onNext(archivoResponse);
            }
        }
        System.out.println("\nEnvío de archivo completado");
        responseStreamObserver.onCompleted();
    }
}
