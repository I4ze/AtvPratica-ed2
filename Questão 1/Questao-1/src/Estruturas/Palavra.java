package Estruturas;

public class Palavra implements Comparable<Palavra> {
    public String palavra;
    public Integer linha;
    public Integer key;

    public Palavra(String palavra, Integer linha, Integer key) {
        this.palavra = palavra;
        this.linha = linha;
        this.key = key;
    }

    @Override
    public int compareTo(Palavra p1){
        if(this.key.equals(p1.key)){
            return 0;
        } else if(this.key < p1.key){
            return -1;
        } else{
            return 1;
        }
    }

    public String toString(){
        return palavra+"-"+linha+" ";
    }
}
