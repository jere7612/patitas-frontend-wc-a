package pe.edu.cibertec.patitas_frontend_wc_a.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.cibertec.patitas_frontend_wc_a.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc_a.dto.LoginResponseDTO;

@FeignClient(name = "autenticacion", url = "http://localhost:8081/autenticacion", configuration = FeignClient.class)
public interface AutenticacionClient {

    @PostMapping("/login")
    LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO);

}
