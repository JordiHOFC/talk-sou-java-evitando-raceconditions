package github.com.br.jordihofc.airline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PoltronaRepository extends JpaRepository<Poltrona,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Poltrona p where p.id = :id")
    Optional<Poltrona> findByIdWithPessimisticLock(Long id);
}
