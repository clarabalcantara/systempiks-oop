/*
 * Programaçao Orientada a Objetos 2025.1
 * Prof. Fausto Ayres
 * Discente Clara Brito Palmeira Nunes de Alcântara
 */

package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lancamento {
    protected LocalDateTime datahora; protected double valor; protected Character tipo; // tipo = + ou -

    // const original
    public Lancamento(double valor, Character tipo) {
        this.datahora = LocalDateTime.now(); this.valor = valor; this.tipo = tipo;
    }

    // const com data e hora -
    public Lancamento(LocalDateTime datahora, double valor, Character tipo) {
        this.datahora = datahora; this.valor = valor; this.tipo = tipo;
    }

    public LocalDateTime getDatahora() {return datahora;}

    public double getValor() {return valor;}

    public Character getTipo() {return tipo;}

    public String toString() { // olhei na documentação pra formatar melhor
    	return datahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))+" "+valor+" "+tipo;

    }

}
