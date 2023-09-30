package model;

public class TarefaModel {
    private int id;
    private String titulo;
    private String desc;
    private char statu;

    public TarefaModel(int id, String titulo, String desc, char statu) {
        this.id = id;
        this.titulo = titulo;
        this.desc = desc;
        this.statu = statu;
    }

    public TarefaModel(String titulo, String desc, char statu) {
        this.titulo = titulo;
        this.desc = desc;
        this.statu = statu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public char getStatu() {
        return statu;
    }

    public void setStatu(char statu) {
        this.statu = statu;
    }

    @Override
    public String toString() {
        return "TarefaModel{" + "id = " + id + ", titulo = '" + titulo + '\'' + ", desc = '" + desc + '\'' + ", statu = " + statu + '}';
    }
}
