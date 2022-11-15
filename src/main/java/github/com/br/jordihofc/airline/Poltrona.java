package github.com.br.jordihofc.airline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Poltrona {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String descricao;
    @Column(nullable = false)
    private String reservadoPara;
    @Column(nullable = false)
    private boolean reservado = false;


    public Poltrona(String descricao) {
        this.descricao = descricao;
    }

    @Deprecated
    public Poltrona() {
    }

    public Long getId() {
        return id;
    }

    //mostrar este metodo
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
