package br.sisacademico.dao;

import br.sisacademico.model.Aluno;
import br.sisacademico.model.Curso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlunoDao {

    //statement = query no java
    private static Statement stm = null;

    public ArrayList<Aluno> getAlunos(Integer... idCurso) throws SQLException {
        ArrayList<Aluno> alunos = new ArrayList<>();

        String query = "SELECT"
                + "    ALUNO.ID as ID_ALUNO, ALUNO.RA, ALUNO.NOME, CURSO.ID as ID_CURSO, CURSO.NOME_CURSO, CURSO.TIPO_CURSO"
                + "    FROM"
                + "    TB_ALUNO AS ALUNO"
                + "    INNER JOIN TB_CURSO AS CURSO"
                + "    ON ALUNO.ID_CURSO = CURSO.ID";
        
                query += "    ORDER BY ALUNO.NOME";
                
                stm = ConnectionFactory.getConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet resultados = stm.executeQuery(query);
                
                while(resultados.next()){
                Aluno a = new Aluno();
                Curso c = new Curso();
                
                a.setIdAluno(resultados.getInt("ID_ALUNO"));
                a.setRa(resultados.getInt("RA"));
                a.setNome(resultados.getString("NOME"));
                
                c.setIdCurso(resultados.getInt("ID_CURSO"));
                c.setNomeCurso(resultados.getString("NOME_CURSO"));
                c.setTipoCurso(resultados.getString("TIPO_CURSO"));
                
                a.setCurso(c);
                
                alunos.add(a);
                }
                return alunos;
    }
    
    public boolean deleteAluno(int idAluno) {
        try {
            String query = "DELETE FROM TB_ALUNO WHERE ID = ?";
            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);
            stm.setInt(1, idAluno);
            stm.execute();
            stm.getConnection().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean cadastraAluno(Aluno aluno) {
        try {
            String query
                    = "INSERT INTO TB_ALUNO(RA, NOME, ID_CURSO) VALUES(?,?,?)";

            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);

            stm.setInt(1, aluno.getRa());
            stm.setString(2, aluno.getNome());
            stm.setInt(3, aluno.getCurso().getIdCurso());

            stm.execute();

            stm.getConnection().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean atualizaAluno(int idAlunoAtualizado, String nomeNovo, int idCursoNovo) {
        try {
            String query = "UPDATE TB_ALUNO"
                    + " SET NOME = ?, ID_CURSO = ?"
                    + " WHERE ID = ?";
            
            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);

            stm.setString(1, nomeNovo);
            stm.setInt(2, idCursoNovo);
            stm.setInt(3, idAlunoAtualizado);

            stm.execute();

            stm.getConnection().close();
            
            return true;
        } catch(Exception ex) {
            return false;
        }
    }
    
    
    //atualização por objeto
    public boolean atualizaAluno(Aluno aluno) {
        try {
            String query = "UPDATE TB_ALUNO"
                    + " SET NOME = ?, ID_CURSO = ?"
                    + " WHERE ID = ?";
            
            PreparedStatement stm = ConnectionFactory.getConnection()
                    .prepareStatement(query);

            stm.setString(1, aluno.getNome());
            stm.setInt(2, aluno.getCurso().getIdCurso());
            stm.setInt(3, aluno.getIdAluno());

            stm.execute();

            stm.getConnection().close();
            
            return true;
        } catch(Exception ex) {
            return false;
        }
    }
}
