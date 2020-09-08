package br.com.adriano.sincronizacaoreceita.services;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class SincronizacaoReceitaServiceTest {

    LeitorCSVService leitorCSVServiceSpy;
    EscritorCSVService escritorCSVServiceSpy;
    SincronizacaoReceitaService service;

    @Before
    public void setup() throws IOException {
        leitorCSVServiceSpy = spy(new LeitorCSVService());
        service = new SincronizacaoReceitaService(leitorCSVServiceSpy);
    }

    @Test
    public void deveProcessarComSucesso() throws IOException {
        service.processarAtualizacao("/home/adriano/workspace/sincronizacaoreceita/src/test/import/contas.csv");
        verify(leitorCSVServiceSpy, times(4)).BuscarProximaConta();
    }

    @Test
    public void naoDeveProcessarQuandoNaoForUmArquivoValido() throws IOException {
        service.processarAtualizacao("");
        verify(leitorCSVServiceSpy, never()).BuscarProximaConta();
    }
}