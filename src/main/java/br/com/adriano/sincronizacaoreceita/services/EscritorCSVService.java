package br.com.adriano.sincronizacaoreceita.services;

import br.com.adriano.sincronizacaoreceita.model.ContaCSV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EscritorCSVService {
    private String caminho;
    BufferedWriter bw;
    private PrintWriter pw;

    public EscritorCSVService(String caminho) throws IOException {
        this.caminho = caminho.substring(0, caminho.length()-4)+"Retorno.csv";
        FileWriter fw = new FileWriter(this.caminho, true);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw);
        pw.println("agencia;conta;saldo;status;processado;");
        pw.flush();
        //pw.close();
    }

    public void escreverRetornoProcessamento(ContaCSV contaCSV) {
        try {


            pw.println(contaCSV.getAgencia() + ";" + contaCSV.getConta() + ";" + String.valueOf(contaCSV.getSaldo()) + ";" + contaCSV.getStatus() + ";" + contaCSV.getProcessamento() + ";");
            pw.flush();

        } catch (Exception E) {
            System.out.println("Erro ao gravar chamado" + E.getMessage());
        }
    }

    public void fecharArquivo() {
        pw.close();
    }
}
