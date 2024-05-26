import java.io.*;

class Main {
    public static void main(String[] args) {
        String filename = "btree.txt";
        BTree t = new BTree(3); // Suponha que o grau mínimo t seja 3
        t.insert(10);
        t.insert(20);
        t.insert(5);
        t.insert(6);
        t.insert(12);
        t.insert(30);
        t.insert(7);
        t.insert(17);

        // Verifica se o arquivo existe antes de tentar carregar
       // if (new File(filename).exists()) {
        //    loadTreeFromFile(filename, t);
        //}


        t.saveTreeToFile(filename);

        //t.Show(t.root);
       //t.Remove(t.root,10);
        //t.Show(t.root);

        int key = 6;
        if (t.bTreeSearch(t.root, key) != null) {
            System.out.println(key + " | Present");
        } else {
            System.out.println(key + " | Not Present");
        }

        key = 15;
        if (t.bTreeSearch(t.root, key) != null) {
            System.out.println(key + " | Present");
        } else {
            System.out.println(key + " | Not Present");
        }

        // Salva a árvore em um arquivo
        t.saveTreeToFile("btree.txt");

    }
    //public static BTree<Integer> loadTreeFromFile(String filename, BTree<Integer> btree) {
    //    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
    //        String line;
    //        while ((line = br.readLine()) != null) {
//                String[] parts = line.trim().split("\\s+");
//                for (String part : parts) {
//                    if (!part.isEmpty()) {
//                        Integer key = Integer.parseInt(part);
//                        btree.insert(key);  // Insere cada chave usando a função de inserção
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Erro ao carregar a árvore B: " + e.getMessage());
//        }
//        return btree;
//    }
}
