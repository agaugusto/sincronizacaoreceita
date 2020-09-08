package br.com.adriano.sincronizacaoreceita.services;

import br.com.adriano.sincronizacaoreceita.model.ContaCSV;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class LeitorCSVService {

    private Long start = System.currentTimeMillis();
    private Integer linha = 1;
    private Integer task = 1;
    private FileInputStream inputStream = null;
    private Scanner sc = null;

    ApplicationContext context = new ClassPathXmlApplicationContext(
            "Spring-Config.xml");

    ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context
            .getBean("taskExecutor");

    public void leitorDeArquivoCSV(String caminho) throws FileNotFoundException {
        inputStream = new FileInputStream(caminho);
        sc = new Scanner(inputStream, "UTF-8");
        sc.nextLine();
    }

    public ContaCSV BuscarProximaConta() {
        if(!sc.hasNext()){
            return null;
        }
        String[] line = sc.nextLine().split(";");
        if (line.length < 4) {
            return null;
        }

        ContaCSV contaCSV = ContaCSV.builder()
                .agencia(line[0])
                .conta(line[1].replaceAll("-", ""))
                .saldo(Double.valueOf(line[2].replaceAll(",", ".")))
                .status(line[3]).build();
        return contaCSV;
    }
}
