package ch.dams333.pokerCraft.combinaison;

public class Combinaison {
    private String name;
    private int to;
    private int higher;

    public String getName() {
        return name;
    }

    public int getTo() {
        return to;
    }

    public int getHigher() {
        return higher;
    }

    public Combinaison(String name, int to, int higher) {

        this.name = name;
        this.to = to;
        this.higher = higher;
    }

    public String toBeautifulName(){
        if(name.equalsIgnoreCase("paire")){
            return "une paire";
        }
        if(name.equalsIgnoreCase("2paire")){
            return "deux paires";
        }
        if(name.equalsIgnoreCase("brelan")){
            return "un brelan";
        }
        if(name.equalsIgnoreCase("quinte")){
            return "une quinte (suite)";
        }
        if(name.equalsIgnoreCase("flush")){
            return "une flush (couleur)";
        }
        if(name.equalsIgnoreCase("full")){
            return "un full";
        }
        if(name.equalsIgnoreCase("quarter")){
            return "un carré";
        }if(name.equalsIgnoreCase("quinteflush")){
            return "une quinte flush";
        }if(name.equalsIgnoreCase("quinteflushroyale")){
            return "une QUINTE FLUSH ROYALE";
        }
        if(name.equalsIgnoreCase("card")){
            return "une haute carte";
        }
        return name;
    }

    public String toBeautifulTo(){
        if(to == 11){
            return "au valais";
        }
        if(to == 12){
            return "à la reine";
        }
        if(to == 13){
            return  "au roi";
        }
        if(to == 14){
            return  "à l'as";
        }
        return "à " + to;
    }

    public String toBeautifulBestCard(){
        if(higher == 11){
            return "un valais";
        }
        if(higher == 12){
            return "une reine";
        }
        if(higher == 13){
            return  "un roi";
        }
        if(higher == 14){
            return  "un as";
        }
        return "un " + higher;
    }

    public int combinaisonPower(){
        if(name.equalsIgnoreCase("paire")){
            return 2;
        }
        if(name.equalsIgnoreCase("2paire")){
            return 3;
        }
        if(name.equalsIgnoreCase("brelan")){
            return 4;
        }
        if(name.equalsIgnoreCase("quinte")){
            return 5;
        }
        if(name.equalsIgnoreCase("flush")){
            return 6;
        }
        if(name.equalsIgnoreCase("full")){
            return 7;
        }
        if(name.equalsIgnoreCase("quarter")){
            return 8;
        }if(name.equalsIgnoreCase("quinteflush")){
            return 9;
        }if(name.equalsIgnoreCase("quinteflushroyale")){
            return 10;
        }
        if(name.equalsIgnoreCase("card")){
            return 1;
        }
        return 0;
    }
}
