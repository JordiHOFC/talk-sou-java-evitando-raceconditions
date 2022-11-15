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
public class ReservaPoltronaOptimisticLockTryCatchController {
    @Autowired
    private PoltronaOptmisitcLockingRepository repository;

    @Transactional
    @PostMapping("api/v1/poltronas/{id}/reservas")
    public ResponseEntity<?> reservar(@PathVariable Long id, @RequestParam @NotBlank String usuario) {
        try {
            PoltronaOptimisticLocking poltrona = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Poltrona não existente."));

            if (poltrona.isReservada()) {
                throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Esta poltrona já esta reservada");
            }

            poltrona.setReservado(true);
            poltrona.setReservadoPara(usuario);

            repository.save(poltrona);

            return ResponseEntity.ok().build();

        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new ResponseStatusException(CONFLICT, "Impossivel realizar a reserva, tente novamente", ex);
        }
    }
}
