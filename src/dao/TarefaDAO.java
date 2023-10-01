package dao;

import model.TarefaModel;

import java.sql.*;
import java.util.ArrayList;


public class TarefaDAO {
    String jdbcUrl = "jdbc:mysql://localhost:3306/projeto_revisao";
    String username = "root";
    String password = "24042003";
    ArrayList<TarefaModel> tarefas;

    private boolean check;

    public void addTarefaSQL(TarefaModel tarefaModel) {
        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {

            String query = "INSERT INTO tarefa(titulo, descricao, statu) VALUES (?,?,?);";

            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, tarefaModel.getTitulo());
            ps.setString(2, tarefaModel.getDesc());
            ps.setString(3, String.valueOf(tarefaModel.getStatu()));

            ps.executeUpdate();

            String queryLog = "INSERT INTO log_tarefa(id_tarefa, operacao, mensagem) VALUES (?, 'Add_Tarefa', 'Uma Tarefa Foi Adicionada');";
            PreparedStatement psLog = c.prepareStatement(queryLog);

            psLog.setInt(1, retornId());
            psLog.executeUpdate();
            this.check = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int retornId() {
        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT id FROM tarefa ORDER BY id DESC LIMIT 1;";

            PreparedStatement ps = c.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            this.check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<TarefaModel> listarTarefasSQL() {
        tarefas = new ArrayList<TarefaModel>();

        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT * FROM tarefa;";
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int index = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String desc = rs.getString("descricao");
                String statusString = rs.getString("statu");
                char statusChar = statusString.charAt(0);
                TarefaModel tarefa = new TarefaModel(index, titulo, desc, statusChar);
                tarefas.add(tarefa);
            }

            String queryLog = "INSERT INTO log_tarefa(operacao, mensagem) VALUES ('Listar_Tarefa', 'A Tabela foi Listada');";
            PreparedStatement psLog = c.prepareStatement(queryLog);

            psLog.executeUpdate();
            this.check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    public void removeByIdSQL(int index) {
        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "DELETE FROM tarefa WHERE id = ?;";

            PreparedStatement ps = c.prepareStatement(query);

            ps.setInt(1, index);

            ps.executeUpdate();

            String queryLog = "INSERT INTO log_tarefa(id_tarefa ,operacao, mensagem) VALUES (? ,'Remove_Tarefa', 'A Tarefa foi removida');";
            PreparedStatement psLog = c.prepareStatement(queryLog);

            psLog.setInt(1, index);
            psLog.executeUpdate();
            this.check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTarefa(TarefaModel tarefa) {
        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "UPDATE tarefa SET titulo = ?, descricao = ?, statu = ? WHERE id = ?;";
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDesc());
            ps.setString(3, String.valueOf(tarefa.getStatu()));
            ps.setInt(4, tarefa.getId());

            ps.executeUpdate();

            String queryLog = "INSERT INTO log_tarefa(id_tarefa ,operacao, mensagem) VALUES (? ,'Update_Tarefa', 'A Tarefa Foi Atualizada');";
            PreparedStatement psLog = c.prepareStatement(queryLog);

            psLog.setInt(1, tarefa.getId());
            psLog.executeUpdate();
            this.check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizarTarefaSQL(int index) {
        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "UPDATE tarefa SET statu = 'c' WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, index);

            String queryLog = "INSERT INTO log_tarefa(id_tarefa ,operacao, mensagem) VALUES (? ,'Finalizar_Tarefa', 'A Tarefa foi Marcada como concluida');";
            PreparedStatement psLog = c.prepareStatement(queryLog);

            psLog.setInt(1, index);
            psLog.executeUpdate();

            ps.executeUpdate();
            this.check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDataBase() {
        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "DELETE FROM tarefa;";
            PreparedStatement ps = c.prepareStatement(query);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificaTarefaSQL(int index) {
        int id = 0;

        try (Connection c = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT id FROM tarefa WHERE id = ?;";
            PreparedStatement ps = c.prepareStatement(query);

            ps.setInt(1, index);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
            this.check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (id == index) {
            return true;
        }
        return false;
    }

    public boolean verificaAcao() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
