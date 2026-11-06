package jogoosaventureiros;

import java.util.ArrayList;
import player.Arqueiro;
import player.Curandeiro;
import caracteristicas.Curavel;
import player.Guerreiro;
import player.Mago;
import player.Personagem;

public class JogoOsAventureiros {

    public static void main(String[] args) {
       ArrayList<Personagem> equipe = new ArrayList<>();
       ArrayList<Curavel> curavel = new ArrayList<>();
       
       Guerreiro guerreiro = new Guerreiro("Josesval: O Mal ", 4);
       Mago mago = new Mago("Claudineia: A Sem Ideia ", 5);
       Arqueiro arqueiro = new Arqueiro("Celestino: O Sem Destino ", 3);
       Curandeiro curandeiro = new Curandeiro("Katy Catinga: Te cura na Ginga ", 2);
       
       equipe.add(guerreiro);
       equipe.add(mago);
       equipe.add(arqueiro);
       equipe.add(curandeiro);
       
       System.out.println("Equipe: Lendas do Caos, a ascensao de Josesval: o Mal");
       
       for(Personagem p : equipe){
           p.exibirInformacoes();
           System.out.println("--------------------");
       }
       
       curavel.add(guerreiro);
       curavel.add(mago);
       
       for(Curavel c : curavel){
        c.receberCura(20);
        System.out.println("--------------------");
    }
}
}
