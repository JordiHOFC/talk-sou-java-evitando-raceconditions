package github.com.br.jordihofc.airline.optimistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
public class ReservaPoltronaOptimisticLockController {
    @Autowired
    private PoltronaOptmisitcLockingRepository repository;

    @Transactional
    @PostMapping("api/v1/poltronas/{id}/reservas")
    public ResponseEntity<?> reservar(@PathVariable Long id, @RequestParam @NotBlank String usuario) {

        PoltronaOptimisticLocking poltrona = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Poltrona não existente."));

        if (poltrona.isReservada()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Esta poltrona já esta reservada");
        }

        poltrona.setReservado(true);
        poltrona.setReservadoPara(usuario);

        repository.save(poltrona);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<?> optimisticLockingHandler(ObjectOptimisticLockingFailureException ex) {

        Map<String, String> response = Map.of("mensagem", "Impossivel realizar a reserva, tente novamente");

        return ResponseEntity.status(CONFLICT).body(response);
    }
}
