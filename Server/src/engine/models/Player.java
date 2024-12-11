package engine.models;

public class Player {
    private String nome;

    public Player(String username){
        nome = username;
    }

    public String getNome(){
        return nome;
    }


}
