package github.com.br.jordihofc.airline.naive;

import github.com.br.jordihofc.airline.Poltrona;
import github.com.br.jordihofc.airline.PoltronaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
public class ReservaPoltronaController {
    @Autowired
    private PoltronaRepository repository;

    @Transactional
    @PostMapping("api/v1/poltronas/{id}/reservas")
    public ResponseEntity<?> reservar(@PathVariable Long id, @RequestParam @NotBlank String usuario){

        Poltrona poltrona = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Poltrona não existente."));

        if(poltrona.isReservada()){
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Esta poltrona já esta reservada");
        }

        poltrona.setReservado(true);
        poltrona.setReservadoPara(usuario);

        repository.save(poltrona);

        return ResponseEntity.ok().build();
    }
}
