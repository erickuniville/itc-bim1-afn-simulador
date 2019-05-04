Simulador de autômato finito não determinístico.

Este simulador execute os seguintes passos:

- Lê o arquivo de entrada que contém os dados do autômato (estados, alfabeto, matriz de transição, estado inicial e estado final) que esteja num formato pré-definido.
- Forma a representação da matriz de transição internamente em memória.
- Requisita, via Scanner, uma String contendo a sequência de alfabetos que será executado no autômato.
- Executa os alfabetos no autômato.
- Gera um arquivo com os estados em cada passo e o resultado final (ACEITO ou REJEITADO)

O local do arquivo de input é lido do argumentos do programa (args), é considerado o primeiro argumento. Caso não seja fornecido será lido do "data.txt".

O local do arquivo de output (resultados) é lido do segundo argumento. Caso não seja fornecido será criado em "execution_steps.txt". 