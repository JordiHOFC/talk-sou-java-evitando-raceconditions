package github.com.br.jordihofc.airline.optimistic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PoltronaOptmisitcLockingRepository extends JpaRepository<PoltronaOptimisticLocking,Long> {

}
