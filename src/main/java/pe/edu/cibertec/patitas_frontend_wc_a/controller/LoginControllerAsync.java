package pe.edu.cibertec.patitas_frontend_wc_a.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patitas_frontend_wc_a.client.AutenticacionClient;
import pe.edu.cibertec.patitas_frontend_wc_a.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc_a.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_frontend_wc_a.dto.SignOutRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc_a.dto.SignOutResponseDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")

@CrossOrigin(origins = "http://localhost:5173")

public class LoginControllerAsync {

    @Autowired
    WebClient webClientAutenticacion;

    AutenticacionClient autenticacionClient;

    @PostMapping("/autenticar-async")
    public Mono<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO) {

        // validar campos de entrada
        System.out.println("Consumo webClientAutenticacion");

        if(loginRequestDTO.tipoDocumento() == null || loginRequestDTO.tipoDocumento().trim().length() == 0 ||
                loginRequestDTO.numeroDocumento() == null || loginRequestDTO.numeroDocumento().trim().length() == 0 ||
                loginRequestDTO.password() == null || loginRequestDTO.password().trim().length() == 0) {

            return Mono.just(new LoginResponseDTO("01", "Error: Debe completar correctamente sus credenciales", "", ""));

        }

        try {

            // consumir servicio de autenticación (Del Backend)
            return webClientAutenticacion.post()
                    .uri("/login")
                    .body(Mono.just(loginRequestDTO), LoginRequestDTO.class)
                    .retrieve()
                    .bodyToMono(LoginResponseDTO.class)
                    .flatMap(response -> {

                        if(response.codigo().equals("00")) {
                            return Mono.just(new LoginResponseDTO("00", "", response.nombreUsuario(), ""));
                        } else {
                            return Mono.just(new LoginResponseDTO("02", "Error: Autenticación fallida", "", ""));
                        }

                    });

        } catch(Exception e) {

            System.out.println(e.getMessage());
            return Mono.just(new LoginResponseDTO("99", "Error: Ocurrió un problema en la autenticación", "", ""));

        }

    }
    @PostMapping("/autenticar-Feing")
    public Mono<LoginResponseDTO> autenticarFeing(@RequestBody LoginRequestDTO loginResquestDTO) {
        //validar caompos de entrada

        System.out.println("Consumo autenticacionClient");

        if(loginResquestDTO.tipoDocumento() == null || loginResquestDTO.tipoDocumento().trim().length() ==0 ||
                loginResquestDTO.numeroDocumento() == null || loginResquestDTO.numeroDocumento().trim().length() ==0 ||
                loginResquestDTO.password() == null || loginResquestDTO.password().trim().length() ==0){
            return Mono.just(new LoginResponseDTO("01", "Error : debe completar correctamente el campo","",""));
        }

        try {
            // Consume the authentication service via Feign client
            LoginResponseDTO response = autenticacionClient.login(loginResquestDTO);

            if ("00".equals(response.codigo())) {
                return Mono.just(new LoginResponseDTO("00", "", response.nombreUsuario(), ""));
            } else {
                return Mono.just(new LoginResponseDTO("02", "Error: Autenticación fallida", "", ""));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.just(new LoginResponseDTO("02", "Ocurrió un problema en la autenticación", "", ""));
        }

    }

    @PostMapping("out-async")
    public Mono<SignOutResponseDTO> CerrarSesion(@RequestBody SignOutRequestDTO signOutRequestDTO) {
        try {
            return webClientAutenticacion.post()
                    .uri("/signout")
                    .body(Mono.just(signOutRequestDTO), SignOutRequestDTO.class)
                    .retrieve()
                    .bodyToMono(SignOutResponseDTO.class)
                    .flatMap(signOutResponseDTO -> {
                        if(signOutResponseDTO.codigo()==null){
                            return Mono.just(new SignOutResponseDTO("99", "Error"));
                        }
                        return Mono.just(new SignOutResponseDTO("00", signOutResponseDTO.mensaje()));

                    });


        }catch (Exception e){
            System.out.println(e.getMessage());
            return Mono.just(new SignOutResponseDTO("99", "Error en el Servicio"));
        }

    }

}