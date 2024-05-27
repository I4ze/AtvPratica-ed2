import Estruturas.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int tratamento;
        int x;
        Palavra palavra;
        int keyGenerator = 0;
        boolean rodando = true;

        String caminhoArquivo = "src/entrada.txt";

        Scanner scanner = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado. Inclua o arquivo no diretório de main.java");
            System.exit(1);
        }

        System.out.println("""
                Deseja que o hashing trate colisões por tentativa linear ou quadrática?\

                [0]Tentativa Linear\

                [1]Tentativa Quadrática""");
        tratamento = scanner.nextInt();

        System.out.println("Até quantos caracteres as palavras do arquivo de texto devem ter para serem guardadas nas estruturas?");
        x = scanner.nextInt();

        ArvoreRB<Palavra> arvoreRB = new ArvoreRB<>();
        ArvoreAVL<Palavra> arvoreAVL = new ArvoreAVL<>();
        TabelaHash<Palavra> hash = new HashLinear<>();

        if(tratamento == 0){
            hash = new HashLinear<>();
        } else if (tratamento == 1) {
            hash = new HashingQuadratico<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int numeroLinha = 1;
            while ((linha = reader.readLine()) != null) {
                String[] palavrasLinha = linha.split("\\s+"); // Divide a linha em palavras usando espaços em branco como separador
                for (String palavras : palavrasLinha) {
                    if(palavras.length()>=x){
                        palavra = new Palavra(palavras, numeroLinha, keyGenerator);
                        keyGenerator++;

                        arvoreRB.adiciona(palavra);
                        arvoreAVL.insert(palavra);
                        hash.insere(palavra);
                    }

                }
                numeroLinha++;
            }
        } catch (IOException e) {
            return;
        }

        int opcao;
        while(rodando){
            System.out.printf("""
                    Fator de balanceamento da árvore AVL:%d
                    Escolha uma opção:
                    [1] Mudar fator de balanceamento da árvore
                    [2] Listar palavras
                    [3] sair
                    %n""", arvoreAVL.getFb());
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    int novoFb;
                    System.out.println("Escolha o novo fator de balanceamento: ");
                    novoFb = scanner.nextInt();
                    arvoreAVL.setFb(novoFb);
                    break;
                case 2:
                    System.out.printf("""
                    Escolha uma opção:
                    [1] Árvore AVL
                    [2] Árvore Rubro Negra
                    [3] Hash
                    %n""");
                    int opcaoEstrutura;
                    opcaoEstrutura = scanner.nextInt();

                    switch (opcaoEstrutura){
                        case 1:
                            exibirEmOrdemAVL(arvoreAVL);
                            break;
                        case 2:
                            exibirEmOrdemRB(arvoreRB);
                            break;
                        case 3:
                            exibirEmOrdemHash(hash);
                            break;
                        default:
                            System.out.println("Opção inválida");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("saindo...");
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }

    }

    public static void exibirEmOrdemAVL(ArvoreAVL<Palavra> arvore) {
        exibirEmOrdemAVL(arvore.getRoot());
    }
    private static void exibirEmOrdemAVL(AVLNode<Palavra> node) {
        if(node != null){
            exibirEmOrdemAVL(node.getLeft());
            if(node.getKey() != null){
                System.out.println(node.getKey().toString());
            }
            exibirEmOrdemAVL(node.getRight());
        }
        System.out.println();
    }

    public static void exibirEmOrdemRB(ArvoreRB<Palavra> arvore) {
        exibirEmOrdem(arvore.raiz);
    }

    private static void exibirEmOrdem(RBNode<Palavra> node) {
        if (node != null && !node.equals(ArvoreRB.nil)) {
            exibirEmOrdem(node.esq);
            if (node.v != null) {
                System.out.println(node.v.toString());
            }
            exibirEmOrdem(node.dir);
        }
        System.out.println();
    }

    public static void exibirEmOrdemHash(TabelaHash<Palavra> hash) {
        if (hash != null) {
            for(Palavra palavra : hash.tabela){
                if(palavra != null) {
                    System.out.println(palavra.toString());
                }
            }
        }
        System.out.println();
    }
}
