package br.com.adriano.sincronizacaoreceita;

import br.com.adriano.sincronizacaoreceita.services.LeitorCSVService;
import br.com.adriano.sincronizacaoreceita.services.SincronizacaoReceitaService;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        SincronizacaoReceitaService service = new SincronizacaoReceitaService(new LeitorCSVService());
        service.processarAtualizacao(args[0]);
    }
}
