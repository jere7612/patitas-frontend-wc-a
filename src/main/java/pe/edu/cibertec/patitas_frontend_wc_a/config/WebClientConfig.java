package pe.edu.cibertec.patitas_frontend_wc_a.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    HttpClient httpClient= HttpClient.create()
<<<<<<< HEAD
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,100)//timeout de coneccion
            .responseTimeout(Duration.ofSeconds(1)) //timeout para obtener eñ total de la respuesta
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1,TimeUnit.SECONDS)));//Timeout de espera pra rececepcionar cada paquete
=======
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)//timeout de coneccion
            .responseTimeout(Duration.ofSeconds(10)) //timeout para obtener eñ total de la respuesta
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10,TimeUnit.SECONDS)));//Timeout de espera pra rececepcionar cada paquete
>>>>>>> 448703e2ed4dab9550654a382f7b1816ffe2b51a

    @Bean
    public WebClient webClientAutenticacion(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081/autenticacion")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();


    }
}
