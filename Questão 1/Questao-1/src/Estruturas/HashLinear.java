package Estruturas;

public class HashLinear<T extends Comparable<T>> extends TabelaHash<T>{

    @Override
    public void insere(T key){
        if(getNumItens()+1 > getTam()/2){
            dobraTamanho();
        }
        int hash = hash(key);
        if(tabela[hash] != null){
            for(int i = 1; hash+i<tabela.length; i++){
                if(tabela[hash+i] == null){
                    tabela[hash+i] = key;
                    break;
                }
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
            for (int i = 1; hash + i < tabela.length; i++) {
                if (tabela[hash + i] != null && tabela[hash + i].equals(key)) {
                    elm = tabela[hash + i];
                    tabela[hash + i] = null;
                    break;
                }
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
            contador.incremento(1);//conta um passo da comparação.
        } else {
            contador.incremento(1);//conta um passo da primeira comparação.
            for (int i = 1; hash + i < tabela.length; i++) {
                if (tabela[hash + i] != null && tabela[hash + i].equals(key)) {
                    elm = tabela[hash + i];
                    break;
                }
                contador.incremento(1);//conta mais um passo a cada iteração até encontrar o elemento ou não
            }
        }
        setPassos(contador.getPassos());
        return elm;
    }
}
