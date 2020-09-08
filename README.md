#API de importação e atualização na Receita.

Para executar, deve ser utilizardo o pacote gerado conforme o exemplo.

java -jar sincronizacaoreceita-0.0.1-SNAPSHOT.jar /caminho/arquivo.csv

O arquivo deve estar no formato abaixo.
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I

O processamento já desconsidera a primeira linha do arquivo.
