package controller;

import dao.TarefaDAO;
import model.TarefaModel;
import view.TarefaView;

import java.util.ArrayList;
import java.util.Scanner;

public class TarefaController {
    private final TarefaView tarefaView;
    private final TarefaDAO tarefaDAO;

    public TarefaController() {
        tarefaView = new TarefaView();
        tarefaDAO = new TarefaDAO();
        menu();
    }

    public void addTarefa(String titulo, String desc, char statu) {
        var tarefa = new TarefaModel(titulo, desc, statu);
        tarefaDAO.addTarefaSQL(tarefa);
        tarefaView.tarefaAdicionada();
    }

    public void listarTarefas() {
        ArrayList<TarefaModel> tarefas = tarefaDAO.listarTarefasSQL();
        if (tarefas.isEmpty()) {
            tarefaView.tabelaVazia();
            return;
        }
        tarefaView.listarTarefas(tarefas);
    }

    public void removeTarefa(int index) {
        if (tarefaDAO.verificaTarefaSQL(index)) {
            tarefaDAO.removeByIdSQL(index);
            tarefaView.tarefaRemovida();
            return;
        }
        tarefaView.tarefaNaoEncontrada();
    }


    public void atualizaTarefa(int index, String titulo, String desc, char statu) {
        if (tarefaDAO.verificaTarefaSQL(index)) {
            var tarefa = new TarefaModel(index, titulo, desc, statu);
            tarefaDAO.updateTarefa(tarefa);
            return;
        }
        tarefaView.tarefaNaoEncontrada();
    }

    public void finalizaTarefa(int index) {
        if (tarefaDAO.verificaTarefaSQL(index)) {
            tarefaDAO.finalizarTarefaSQL(index);
            tarefaView.tarefaFinalizada();
            return;
        }
        tarefaView.tarefaNaoEncontrada();
    }


    public void menu() {
        int check;
        Scanner input = new Scanner(System.in);

        while (true) {
            tarefaView.menu();
            check = input.nextInt();
            input.nextLine();
            if (check == -1) {
                tarefaView.encerrando();
                input.close();
                break;
            }
            switch (check) {
                case 1 -> {
                    tarefaView.enseriTitulo();
                    String titulo = input.nextLine();
                    tarefaView.enseriDesc();
                    String desc = input.nextLine();
                    tarefaView.enseriStatus();
                    char status = input.next().charAt(0);
                    if (Character.toUpperCase(status) == 'A' || Character.toUpperCase(status) == 'P' || Character.toUpperCase(status) == 'C') {
                        addTarefa(titulo, desc, status);
                    } else {
                        tarefaView.digiteStatusCorreto();
                    }
                }
                case 2 -> listarTarefas();
                case 3 -> {
                    tarefaView.digiteId();
                    int index = input.nextInt();
                    removeTarefa(index);
                }
                case 4 -> {
                    tarefaView.enseriIndex();
                    int index = input.nextInt();
                    input.nextLine();
                    tarefaView.enseriTitulo();
                    String titulo = input.nextLine();
                    tarefaView.enseriDesc();
                    String desc = input.nextLine();
                    tarefaView.enseriStatus();
                    char status = input.next().charAt(0);
                    if (Character.toUpperCase(status) == 'A' || Character.toUpperCase(status) == 'P' || Character.toUpperCase(status) == 'C') {
                        atualizaTarefa(index, titulo, desc, status);
                    } else {
                        tarefaView.digiteStatusCorreto();
                    }
                }

                case 5 -> {
                    tarefaView.DigiteIdFinalizarTarefa();
                    int index = input.nextInt();
                    finalizaTarefa(index);
                }
            }
        }
    }
}
