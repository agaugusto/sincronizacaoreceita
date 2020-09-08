package br.com.adriano.sincronizacaoreceita.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaCSV {
    private String agencia;
    private String conta;
    private Double saldo;
    private String status;
}
