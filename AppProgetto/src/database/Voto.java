package database;

public class Voto {
    private int voto;
    private String materia;

    public Voto(int voto, String materia){
        setVoto(voto);
        setMateria(materia);
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getVoto() {
        return voto;
    }

    public String getMateria() {
        return materia;
    }
}
