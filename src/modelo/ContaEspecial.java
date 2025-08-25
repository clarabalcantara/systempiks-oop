/*
 * Programaçao Orientada a Objetos 2025.1
 * Prof. Fausto Ayres
 * Discente Clara Brito Palmeira Nunes de Alcântara
 */

package modelo;

public class ContaEspecial extends Conta {
    protected double limite;

    // const original
    public ContaEspecial(int id, String chavePiks, double saldo, Cliente cliente, double limite) {
        super(id, chavePiks, saldo, cliente); this.limite = limite;
    }

    // const sem cliente* mudança depois 
    public ContaEspecial(int id, String chavePiks, double saldo, double limite) {
        super(id, chavePiks, saldo); this.limite = limite;
    }

    public ContaEspecial(int id, String chavePiks, double limite) {
        super(id, chavePiks, 0); this.limite = limite;
    }

    @Override
    public void debitar(double valor) {
        if (this.getSaldo() + this.limite >= valor) {
            this.setSaldo(this.getSaldo() - valor);
            Lancamento lancDebito = new Lancamento(valor, '-'); this.adicionar(lancDebito);
            return;
        }
        throw new IllegalStateException("Saldo insuficiente para debitar. Saldo não pode ser menor que o limite negativo");
    }

    public double getLimite() {return limite;}

    public void setLimite(double limite) {
        this.limite = limite;
    }
}