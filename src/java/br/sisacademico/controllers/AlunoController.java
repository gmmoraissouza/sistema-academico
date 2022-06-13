package br.sisacademico.controllers;

import br.sisacademico.dao.AlunoDao;
import br.sisacademico.dao.CursoDao;
import br.sisacademico.model.Aluno;
import br.sisacademico.model.Curso;
import br.sisacademico.utils.AcaoDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AlunoController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            AcaoDao act = AcaoDao.valueOf(request.getParameter("acao"));
            AlunoDao aDao = new AlunoDao();
            CursoDao cDao = new CursoDao();
            HttpSession session = request.getSession();
            Aluno aluno;
            int idAluno;

            switch (act) {
                case LEITURA:
                    ArrayList<Aluno> alunos;

                    String url = "./relatorios/aluno.jsp";
                    if (request.getParameter("idCurso") == null) {
                        alunos = aDao.getAlunos();
                    } else {
                        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                        alunos = aDao.getAlunos(idCurso);
                        url += "?idCurso=" + idCurso;
                    }

                    session.setAttribute("listaDeAlunos", alunos);

                    response.sendRedirect(url);
                    break;
                case EXCLUSAO:
                    idAluno = Integer.parseInt(request.getParameter("idAluno"));
                    aDao.deleteAluno(idAluno);
                    if (request.getParameter("idCurso") != null) {
                        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                        response.sendRedirect("./relatorios/loader.jsp?pagina=aluno&idCurso=" + idCurso);
                    } else {
                        response.sendRedirect("./relatorios/loader.jsp?pagina=aluno");
                    }

                    break;
                case CARREGAMENTO:
                    session.setAttribute("listaDeCursos", cDao.getCursos());

                    if (request.getParameter("idAluno") != null) {
                        String editParams = String.format("?idAluno=%s&nome=%s&ra=%s&idCurso=%s",
                                request.getParameter("idAluno"),
                                request.getParameter("nome"),
                                request.getParameter("ra"),
                                request.getParameter("idCurso"));
                        response.sendRedirect("./cadastros/aluno.jsp" + editParams);
                    } else {
                        response.sendRedirect("./cadastros/aluno.jsp");
                    }

                    break;
                case CADASTRO:
                    aluno = new Aluno();
                    aluno.setRa(Integer.parseInt(request.getParameter("raAluno")));
                    aluno.setNome(request.getParameter("nomeAluno"));
                    aluno.setCurso(new Curso(Integer.parseInt(request.getParameter("idCurso")), null, null));
                    if (aDao.cadastraAluno(aluno)) {
                        response.sendRedirect("./relatorios/loader.jsp?pagina=aluno");
                    }
                    break;
                case EDICAO:
                    aluno = new Aluno();
                    aluno.setIdAluno(Integer.parseInt(request.getParameter("idAluno")));
                    aluno.setNome(request.getParameter("nomeAluno"));
                    aluno.setCurso(new Curso(Integer.parseInt(request.getParameter("idCurso")), null, null));
                    if (aDao.atualizaAluno(aluno)) {
                        response.sendRedirect("./relatorios/loader.jsp?pagina=aluno");
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
