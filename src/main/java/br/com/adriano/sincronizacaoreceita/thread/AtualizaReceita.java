package br.com.adriano.sincronizacaoreceita.thread;

import br.com.adriano.sincronizacaoreceita.services.ReceitaService;
import br.com.adriano.sincronizacaoreceita.model.ContaCSV;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AtualizaReceita implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizaReceita.class);
    String name;
    ContaCSV contaCSV;
    private ReceitaService receitaService = new ReceitaService();

    public AtualizaReceita(String name, ContaCSV contaCSV) {
        this.name = name;
        this.contaCSV = contaCSV;
    }

    @SneakyThrows
    @Override
    public void run() {
        Integer quantidadeTentativas;
        try {
            quantidadeTentativas = 0;
            while (quantidadeTentativas <= 3) {
                try {
                    if (!receitaService.atualizarConta(contaCSV.getAgencia(), contaCSV.getConta(), contaCSV.getSaldo(), contaCSV.getStatus())) {
                        String erro = String.format("Não foi possível atualizar os dados. Agência: %s, Conta: %s, Saldo: %s e Status: %s", contaCSV.getAgencia(), contaCSV.getConta(), String.valueOf(contaCSV.getSaldo()), contaCSV.getStatus());
                        LOGGER.error(erro);
                    }
                    // LOGGER.debug("Task: " + name + " Finalizado!");
                    break;
                } catch (RuntimeException e) {
                    if (quantidadeTentativas == 3) {
                        String erro = String.format("Task: " + name + " Erro ao atualizar os dados na Receita. Agência: %s, Conta: %s, Saldo: %s e Status: %s", contaCSV.getAgencia(), contaCSV.getConta(), String.valueOf(contaCSV.getSaldo()), contaCSV.getStatus());
                        throw new Exception(erro);
                    }
                    String erro = String.format("Task: " + name + " Erro no serviço, nova tentativa. Agência: %s, Conta: %s, Saldo: %s e Status: %s", contaCSV.getAgencia(), contaCSV.getConta(), String.valueOf(contaCSV.getSaldo()), contaCSV.getStatus());
                    LOGGER.warn(erro);
                    quantidadeTentativas++;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

}