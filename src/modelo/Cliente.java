/*
 * Programaçao Orientada a Objetos 2025.1
 * Prof. Fausto Ayres
 * Discente Clara Brito Palmeira Nunes de Alcântara
 */

package modelo;
public class Cliente {
    protected int cpf; protected String nome; protected Conta conta;

    public Cliente(int cpf, String nome) { this.cpf = cpf; this.nome = nome;}

    public void setConta(Conta cta) { this.conta = cta;}

    public Conta getConta() { return this.conta;}

    public int getCpf() {return this.cpf;}

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getNome() {return this.nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }
}