package github.com.br.jordihofc.airline.optimistic;

import javax.persistence.*;

@Entity
public class PoltronaOptimisticLocking {
    @Id
    @GeneratedValue
    private Long id;
    private String descricao;
    private String reservadoPara;
    private boolean reservado = false;


    @Version
    private Long version;

    public boolean isReservada() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado=reservado;
    }

    public void setReservadoPara(String reservadoPara) {
        this.reservadoPara = reservadoPara;
    }
}
