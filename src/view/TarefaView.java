package view;

import model.TarefaModel;

import java.util.ArrayList;

public class TarefaView {

    public void listarTarefas(ArrayList<TarefaModel> tarefas) {
        System.out.println("\n------------------ Lista de Tarefas ------------------");
        for (TarefaModel t : tarefas) {
            System.out.println(t.toString());
        }
        System.out.println("-------------------------------------------------------\n");
    }

    public void menu() {
        System.out.println("1 - Adicionar Tarefa");
        System.out.println("2 - Listar Tarefas");
        System.out.println("3 - Remover Tarefas");
        System.out.println("4 - Atualizar Tarefas");
        System.out.println("5 - Finalizar Tarefas");
        System.out.println("0 - Finalizar Sistema");
        System.out.print("Escolha uma opção: ");
    }

    public void encerrando() {
        System.out.println("Encerrando...");
    }

    public void enseriTitulo() {
        System.out.print("Digite o titulo da tarefa: ");
    }

    public void enseriDesc() {
        System.out.print("Digite a descrição da tarefa: ");
    }

    public void enseriIndex() {
        System.out.println("Digite o id da tarefa a ser editada");
    }

    public void enseriStatus() {
        System.out.print("Digite o satus da tarefa (A)Aberto (P)Pendente (C)Concluido: ");
    }

    public void digiteStatusCorreto() {
        System.out.println("Digite o status corretamente!");
    }


    public void digiteId() {
        System.out.print("Digite o id da tarefa para remover: ");
    }

    public void DigiteIdFinalizarTarefa() {
        System.out.print("Digite O id da tarefa que voce quer marcar como concluida: ");
    }



    public void tarefaNaoEncontrada() {
        System.out.println("\n---------- Tarefa Não Encontrada ----------\n");
    }

    public void acaoRealizada() {
        System.out.println("\n---------- Ação Realizada Com Sucesso ----------\n");
    }

    public void listaVazia() {
        System.out.println("\n---------- Nenhum Dado Foi Encontrado ----------\n");
    }




}
