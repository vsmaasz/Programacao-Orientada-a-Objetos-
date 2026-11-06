package player;

public abstract class Personagem {
       
        private String nome;
        int nivel;

    public Personagem(String nome, int nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return nivel;
    }
        
    public void exibirInformacoes(){
        System.out.println("Nome: " + getNome() +"| Nivel: " + getNivel());
        System.out.println(atacar());
        System.out.println("Dano: " + calcularDano());
}
    public abstract String atacar();
    public abstract int calcularDano();
    
}
