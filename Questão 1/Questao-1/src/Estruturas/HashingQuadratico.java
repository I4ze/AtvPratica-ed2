package Estruturas;

public class HashingQuadratico<T extends Comparable<T>> extends TabelaHash<T>{
    int c1 = 1; // constantes de incremento
    int c2 = 3;

    @Override
    public void insere(T key){
        if(getNumItens()+1 > getTam()/2){
            dobraTamanho();
        }
        int hash = hash(key);
        if(tabela[hash] != null){
            int novoHash = rehashing(hash, 0);
            boolean adicionado = false;
            for(int i = 0; !adicionado; i++){
                if(tabela[novoHash] == null){
                    tabela[novoHash] = key;
                    adicionado = true;
                }
                novoHash = rehashing(hash, i);
            }
        }else{
            tabela[hash] = key;
        }
        setNumItens(getNumItens() + 1);
    }

    @Override
    public T remove(T key) {
        int hash = hash(key);
        T elm = null;
        if (tabela[hash] != null && tabela[hash].equals(key)) {
            elm = tabela[hash];
            tabela[hash] = null;
        } else {
            int novoHash = rehashing(hash, 0);
            boolean removido = false;
            for(int i = 0; !removido; i++){
                if(tabela[novoHash].equals(key)){
                    elm = tabela[novoHash];
                    tabela[novoHash] = null;
                    removido = true;
                }
                novoHash = rehashing(hash, i);
            }
        }
        return elm;
    }

    @Override
    public T busca(T key) {
        ContaPassos contador = new ContaPassos();
        int hash = hash(key);
        T elm = null;
        if (tabela[hash] != null && tabela[hash].equals(key)) {
            elm = tabela[hash];
            contador.incremento(1);//um incremento da comparação
        } else {
            int novoHash = rehashing(hash, 0);
            boolean encontrado = false;
            for(int i = 0; !encontrado; i++){
                if(tabela[novoHash].equals(key)){
                    elm = tabela[novoHash];
                    encontrado = true;
                }
                contador.incremento(1);//incremento de 1 no loop até encontrar o elemento ou nao.
                novoHash = rehashing(hash, i);
            }
        }
        setPassos(contador.getPassos());
        return elm;
    }

    private int rehashing(int hash, int tentativa){
        return (hash + c1 * tentativa + c2 * tentativa * tentativa) % this.getTam();
    }
}
