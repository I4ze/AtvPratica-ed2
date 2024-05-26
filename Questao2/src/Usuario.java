public class Usuario implements Comparable<Usuario>{
    int id_usuario;
    String login;
    String nome;
    String email;
    String data_de_nasc;
    int foto;

    public Usuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    @Override
    public int compareTo(Usuario usuario) {
        return Integer.compare(this.id_usuario, usuario.id_usuario);
    }
}
