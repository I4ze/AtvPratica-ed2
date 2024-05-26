package Estruturas;

public class Palavra implements Comparable<Palavra> {
    public String palavra;
    public Integer linha;

    public Palavra(String palavra, Integer linha) {
        this.palavra = palavra;
        this.linha = linha;
    }

    @Override
    public int compareTo(Palavra p1) {
        if(this.linha.equals(p1.linha)){
            return this.palavra.compareTo(p1.palavra);
        } else if(this.linha < p1.linha){
            return -1;
        } else{
            return 1;
        }
    }

    public String toString(){
        return palavra+"-"+linha+" ";
    }
}
