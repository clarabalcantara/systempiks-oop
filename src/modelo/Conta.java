/*
 * Programaçao Orientada a Objetos 2025.1
 * Prof. Fausto Ayres
 * Discente Clara Brito Palmeira Nunes de Alcântara
 */

package modelo;
import java.util.ArrayList;

public class Conta {
    protected int id; protected String chavePiks; protected double saldo; protected Cliente cliente; protected ArrayList<Lancamento> lancamentos;

    // const original
    public Conta(int id, String chavePiks, double saldo, Cliente cliente) {
        this.id = id; this.chavePiks = chavePiks; this.saldo = saldo;
        
        this.cliente = cliente; cliente.setConta(this);
        this.lancamentos = new ArrayList<>();
    }
    public Conta(int id, String chavePiks, Cliente cliente) {
        this.id = id; this.chavePiks = chavePiks;
        this.cliente = cliente; this.lancamentos = new ArrayList<>();
        cliente.setConta(this);
    }

    // consts sem cliente* mudança depois 
    public Conta(int id, String chavePiks, double saldo) {
        this.id = id; this.chavePiks = chavePiks;
        this.saldo = saldo; this.lancamentos = new ArrayList<>();
    }

    public Conta(int id, String chavePiks) {
        this.id = id; this.chavePiks = chavePiks;
        this.saldo = 0; this.lancamentos = new ArrayList<>();
    }

    public void creditar(double valor) {
        this.saldo += valor; Lancamento lancCredito= new Lancamento(valor, '+'); this.adicionar(lancCredito);}

    public void debitar(double valor) {
        if((this.getSaldo()-valor) >= 0) {
            this.saldo -= valor;
            Lancamento lancDebito = new Lancamento(valor, '-'); this.adicionar(lancDebito);
            return;
        }
        throw new IllegalStateException("Saldo insuficiente para debitar. Saldo não pode ser menor 0");
    }

    public void adicionar(Lancamento lanc) {this.lancamentos.add(lanc);}

    public void transferir(Conta destino, double valor) { // serve tanto p conta comum qnt p especial
        if (this == destino) {
            throw new IllegalStateException("Entre a mesma conta n pode");

        }
        this.debitar(valor);
        destino.creditar(valor);
    }

    // get
    public int getId() {return id;}

    public String getChavePiks() {return chavePiks;}

    public double getSaldo() {return saldo;}

    public Cliente getCliente() { return cliente;}

    public ArrayList<Lancamento> getLancamentos() {return lancamentos;}
    // set
    public void setCliente(Cliente cliente) {this.cliente = cliente;}


    public void setSaldo(double saldo) {this.saldo = saldo;}

    public void setChavePiks(String chavePiks) {this.chavePiks = chavePiks;}
}