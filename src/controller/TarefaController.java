package controller;

import dao.TarefaDAO;
import model.TarefaModel;
import view.TarefaView;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
        verificaAcao();
    }

    public void listarTarefas() {
        ArrayList<TarefaModel> tarefas = tarefaDAO.listarTarefasSQL();
        if (tarefas.isEmpty()) {
            tarefaView.listaVazia();
            return;
        }
        tarefaView.listarTarefas(tarefas);
    }

    public void removeTarefa(int index) {
        if (tarefaDAO.verificaTarefaSQL(index)) {
            tarefaDAO.removeByIdSQL(index);
            return;
        }
        tarefaView.tarefaNaoEncontrada();
    }


    public void atualizaTarefa(int index, String titulo, String desc, char statu) {
        if (tarefaDAO.verificaTarefaSQL(index)) {
            var tarefa = new TarefaModel(index, titulo, desc, statu);
            tarefaDAO.updateTarefa(tarefa);
            verificaAcao();
            return;
        }
        tarefaView.tarefaNaoEncontrada();
    }

    public void finalizaTarefa(int index) {
        if (tarefaDAO.verificaTarefaSQL(index)) {
            tarefaDAO.finalizarTarefaSQL(index);
            verificaAcao();
            return;
        }
        tarefaView.tarefaNaoEncontrada();
    }

    public void clearDataBase() {
        tarefaDAO.clearDataBase();
        tarefaView.acaoRealizada();
    }

    public void verificaAcao() {
        if (tarefaDAO.verificaAcao()) {
            tarefaView.acaoRealizada();
            tarefaDAO.setCheck(false);
        }
    }

    public void menu() {
        int check;
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
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

                    case 6 -> {
                        String verify;
                        System.out.print("Voce Quer Limpar A Base de Dados? (s)Sim (n) Nao");
                        verify = input.nextLine();
                        if(verify.equalsIgnoreCase("s")) {
                            System.out.println("Voce tem certeza quer quer deletar os dados (SIM)Sim (NAO) Nao");
                            verify = input.nextLine();
                            if(verify.equalsIgnoreCase("sim")) {
                                System.out.println("VOCE REALMENTE TEM CERTEZA QUE DESEJA LIMPAR O BANCO DE DADOS? para isso digite: \nEU DESEJO EXCLUIR OS DADOS");
                                verify = input.nextLine();
                                if(verify.equalsIgnoreCase("eu desejo excluir os dados")) {
                                    clearDataBase();
                                } else {
                                    System.out.println("nada foi feito, digite corretamente");
                                }
                            }
                        }
                    }
                }
            } catch (InputMismatchException e) {
                e.printStackTrace();
                input.nextLine();
            }
        }
    }
}
