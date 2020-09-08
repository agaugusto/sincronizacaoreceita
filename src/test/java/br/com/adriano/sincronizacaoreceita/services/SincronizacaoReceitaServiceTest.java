package br.com.adriano.sincronizacaoreceita.services;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SincronizacaoReceitaServiceTest {

    LeitorCSVService leitorCSVServiceSpy;
    SincronizacaoReceitaService service;

    @Before
    public void setup(){
        leitorCSVServiceSpy = spy(new LeitorCSVService());
        service = new SincronizacaoReceitaService(leitorCSVServiceSpy);
    }

    @Test
    public void deveProcessarComSucesso(){
        service.processarAtualizacao("/home/adriano/workspace/sincronizacaoreceita/src/test/import/contas.csv");
        verify(leitorCSVServiceSpy, times(4)).BuscarProximaConta();
    }

    @Test
    public void naoDeveProcessarQuandoNaoForUmArquivoValido(){
        service.processarAtualizacao("");
        verify(leitorCSVServiceSpy, never()).BuscarProximaConta();
    }
}