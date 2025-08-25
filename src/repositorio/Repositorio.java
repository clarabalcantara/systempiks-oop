/*
 * Programaçao Orientada a Objetos 2025.1
 * Prof. Fausto Ayres
 * Discente Clara Brito Palmeira Nunes de Alcântara
 */
package repositorio;

import modelo.Cliente;
import modelo.Conta;
import modelo.Lancamento;
import java.util.TreeMap;
import modelo.ContaEspecial;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

public class Repositorio {
    // deixar estático
    private static TreeMap<String, Conta> contasPIKS = new TreeMap<>(); private static TreeMap<Integer, Cliente> clientesCPF = new TreeMap<>();
    public static void adicionarConta(Conta cta) { contasPIKS.put(cta.getChavePiks(), cta);}

    public static void removerConta(Conta cta) {
        if (cta.getSaldo() == 0) {
            contasPIKS.remove(cta.getChavePiks());
            clientesCPF.remove(cta.getCliente().getCpf());
            cta.getLancamentos().clear();
        }
    }

    public static Conta localizarConta(String chavePiks) { return contasPIKS.get(chavePiks);}

    public static void adicionarCliente(Cliente cli) {clientesCPF.put(cli.getCpf(), cli);}

    public static void removerCliente(Cliente cli) {clientesCPF.remove(cli.getCpf());}

    public static Cliente localizarCliente(int cpf) {return clientesCPF.get(cpf);}

    public static ArrayList<Conta> listarContas() {return new ArrayList<>(contasPIKS.values());}

    public static ArrayList<Cliente> listarClientes() {return new ArrayList<>(clientesCPF.values());}

    // parte .csv - feita pelo professor
    public static void lerObjetos() {
        try {
            File f1 = new File((new File("data/contasPIKS.csv")).getCanonicalPath());
            File f2 = new File((new File("data/lancamentos.csv")).getCanonicalPath());
            if (!f1.exists() || !f2.exists()) {
                System.out.println("criando arquivo .csv vazio");
                FileWriter arqconta = new FileWriter(f1);
                FileWriter arqlancamento = new FileWriter(f2);
                arqconta.close();
                arqlancamento.close();
                return;
            }
        } catch (Exception var22) {
            throw new RuntimeException("criacao dos arquivos vazios:" + var22.getMessage());
        }

        try {
            File f1 = new File((new File("data/contasPIKS.csv")).getCanonicalPath()); // mudança estava ".\\contasPIKS.csv"
            Scanner arqconta = new Scanner(f1);
            System.out.println("Repositorio - lendo objetos...");

            String linha;
            String[] partes;
            String chave;
            while (arqconta.hasNextLine()) {
                linha = arqconta.nextLine().trim();
                partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                chave = partes[1];
                double saldo = Double.parseDouble(partes[2]);
                double limite = Double.parseDouble(partes[3]);
                int cpf = Integer.parseInt(partes[4]);
                String nome = partes[5];
                Cliente cliente = new Cliente(cpf, nome);
                Conta conta;
                if (limite == 0.0) {
                    conta = new Conta(id, chave, saldo);
                } else {
                    conta = new ContaEspecial(id, chave, saldo, limite);
                }

                cliente.setConta(conta);
                conta.setCliente(cliente);
                adicionarConta(conta);
                adicionarCliente(cliente);
            }

            arqconta.close();
            File f2 = new File((new File("data/lancamentos.csv")).getCanonicalPath()); // mudança estava ".\\lancamentos.csv"
            Scanner arqlan = new Scanner(f2);

            while (arqlan.hasNextLine()) {
                linha = arqlan.nextLine().trim();
                partes = linha.split(";");
                chave = partes[0];
                LocalDateTime datahora = LocalDateTime.parse(partes[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                double valor = Double.parseDouble(partes[2]);
                Character tipo = partes[3].charAt(0);
                Lancamento lanc = new Lancamento(datahora, valor, tipo);
                Conta conta = localizarConta(chave);
                conta.adicionar(lanc);
            }

            arqlan.close();
        } catch (Exception var21) {
            throw new RuntimeException("leitura arquivo de contasPIKS:" + var21.getMessage());
        }
    }

    public static void gravarObjetos() {
        try {
            File f1 = new File((new File("data/contasPIKS.csv")).getCanonicalPath());
            FileWriter arqconta = new FileWriter(f1);
            File f2 = new File((new File("data/lancamentos.csv")).getCanonicalPath());
            FileWriter arqlan = new FileWriter(f2);
            System.out.println("Repositorio - gravando objetos...");
            Iterator<Conta> var7 = contasPIKS.values().iterator();

            while (true) {
                Conta cta;
                do {
                    if (!var7.hasNext()) {
                        arqconta.close();
                        arqlan.close();
                        return;
                    }

                    cta = var7.next();
                    Double limite;
                    if (cta instanceof ContaEspecial esp) {
                        limite = esp.getLimite();
                    } else {
                        limite = 0.0;
                    }

                    String linha = "" + cta.getId() + ";" + cta.getChavePiks() + ";" + cta.getSaldo() + ";" + limite + ";" + cta.getCliente().getCpf() + ";" + cta.getCliente().getNome();
                    arqconta.write(linha + "\n");
                } while (cta.getLancamentos().isEmpty());

                Iterator<Lancamento> var10 = cta.getLancamentos().iterator();

                while (var10.hasNext()) {
                    Lancamento lan = var10.next();
                    String s = lan.getDatahora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    arqlan.write(cta.getChavePiks() + ";" + s + ";" + lan.getValor() + ";" + lan.getTipo() + "\n");
                }
            }
        } catch (Exception var12) {
            throw new RuntimeException("problema na criação do arquivo contasPIKS " + var12.getMessage());
        }
    }

    static public ArrayList<Conta> getContas() {
        return new ArrayList<>(contasPIKS.values());
    }

    static public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientesCPF.values());
    }
}