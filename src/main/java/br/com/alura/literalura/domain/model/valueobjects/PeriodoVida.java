package br.com.alura.literalura.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PeriodoVida {
    private Integer anoNascimento;
    private Integer anoFalecimento;

    public PeriodoVida() {}

    public PeriodoVida(Integer anoNascimento, Integer anoFalecimento) {
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public boolean estaVivoNoAno(int ano) {
        if (anoNascimento == null) return false;
        if (anoFalecimento == null) return ano >= anoNascimento;
        return ano >= anoNascimento && ano <= anoFalecimento;
    }
}