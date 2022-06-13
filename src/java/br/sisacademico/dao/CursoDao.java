package br.sisacademico.dao;

import br.sisacademico.model.Curso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CursoDao {

    private static Statement stm = null;

    public Map<Curso, Integer> getTodosCursosCountAlunos() throws SQLException {

        Map<Curso, Integer> relatorio = new LinkedHashMap<>();

        String query = "SELECT"
                + "    CURSO.ID,"
                + "    CURSO.NOME_CURSO,"
                + "    CURSO.TIPO_CURSO,"
                + "    (SELECT COUNT(*)"
                + "       FROM TB_ALUNO"
                + "      WHERE TB_ALUNO.ID_CURSO = CURSO.ID) AS QTD_ALUNOS"
                + " FROM"
                + "    TB_CURSO AS CURSO"
                + " ORDER BY CURSO.NOME_CURSO";

        stm = ConnectionFactory.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet resultados = stm.executeQuery(query);

        while (resultados.next()) {
            Curso c = new Curso();
            c.setIdCurso(resultados.getInt("ID"));
            c.setNomeCurso(resultados.getString("NOME_CURSO"));
            c.setTipoCurso(resultados.getString("TIPO_CURSO"));
            int qdtAlunos = resultados.getInt("QTD_ALUNOS");

            relatorio.put(c, qdtAlunos);
        }

        stm.getConnection().close();

        return relatorio;
    }
    
    public ArrayList<Curso> getCursos() throws SQLException {

        String query = "SELECT"
                + "    curso.ID,"
                + "    curso.NOME_CURSO,"
                + "    curso.TIPO_CURSO"
                + " FROM"
                + "    TB_CURSO as curso"
                + " ORDER BY"
                + "    curso.NOME_CURSO";

        ArrayList<Curso> cursos = new ArrayList<>();

        stm = ConnectionFactory.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet resultados = stm.executeQuery(query);

        while (resultados.next()) {
            Curso c = new Curso();
            c.setIdCurso(resultados.getInt("ID"));
            c.setNomeCurso(resultados.getString("NOME_CURSO"));
            c.setTipoCurso(resultados.getString("TIPO_CURSO"));

            cursos.add(c);
        }

        stm.getConnection().close();

        return cursos;
    }

    public boolean deleteCurso(int idCurso) {
        try {
            String query = "DELETE FROM TB_CURSO WHERE ID = ?";
            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);
            stm.setInt(1, idCurso);
            stm.execute();
            stm.getConnection().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean cadastraCurso(Curso curso) {
        try {
            String query
                    = "INSERT INTO TB_CURSO(NOME_CURSO, TIPO_CURSO) VALUES(?,?)";

            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);

            stm.setString(1, curso.getNomeCurso());
            stm.setString(2, curso.getTipoCurso());

            stm.execute();

            stm.getConnection().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean atualizaCurso(int idCursoAtualizado, String nomeNovo, String tipoNovo) {
        try {
            String query = "UPDATE TB_CURSO "
                    + "   SET NOME_CURSO = ?, "
                    + "       TIPO_CURSO = ?"
                    + " WHERE ID = ?";
            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);

            stm.setString(1, nomeNovo);
            stm.setString(2, tipoNovo);
            stm.setInt(3, idCursoAtualizado);

            stm.execute();

            stm.getConnection().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    //método alternativo para atualização do curso
    public boolean atualizaCurso(Curso c) {
        try {
            String query = "UPDATE TB_CURSO "
                    + "   SET NOME_CURSO = ?, "
                    + "       TIPO_CURSO = ?"
                    + " WHERE ID = ?";
            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);

            stm.setString(1, c.getNomeCurso());
            stm.setString(2, c.getTipoCurso());
            stm.setInt(3, c.getIdCurso());

            stm.execute();

            stm.getConnection().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}