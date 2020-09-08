package br.com.adriano.sincronizacaoreceita.services;

import br.com.adriano.sincronizacaoreceita.model.ContaCSV;
import br.com.adriano.sincronizacaoreceita.thread.AtualizaReceita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class SincronizacaoReceitaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacaoReceitaService.class);
    private final LeitorCSVService leitorCSVService;
    ApplicationContext context = new ClassPathXmlApplicationContext(
            "Spring-Config.xml");

    ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context
            .getBean("taskExecutor");

    public SincronizacaoReceitaService(LeitorCSVService leitorCSVService) {
        this.leitorCSVService = leitorCSVService;
    }

    public void processarAtualizacao(String caminho) {
        try {
            Integer task = 0;
            leitorCSVService.leitorDeArquivoCSV(caminho.trim());
            ContaCSV contaCSV = leitorCSVService.BuscarProximaConta();
            while (contaCSV != null) {
                taskExecutor.execute(new AtualizaReceita("Thread " + task, contaCSV));
                task ++;
                contaCSV = leitorCSVService.BuscarProximaConta();
            }

            for (; ; ) {
                Thread.sleep(5000);
                if (taskExecutor.getActiveCount() == 0) {
                    LOGGER.info("Processo finalizado!");
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Arquivo não encontrado!");
        } catch (InterruptedException e){
            LOGGER.error("Processamento Interrompido!");
        }
    }
}
