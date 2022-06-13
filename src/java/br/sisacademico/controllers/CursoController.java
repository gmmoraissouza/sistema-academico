package br.sisacademico.controllers;

import br.sisacademico.dao.CursoDao;
import br.sisacademico.model.Curso;
import br.sisacademico.utils.AcaoDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CursoController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            AcaoDao act = AcaoDao.valueOf(request.getParameter("acao"));
            CursoDao cDao = new CursoDao();
            int idCurso = -1;
            String nomeCurso = "";
            String tipoCurso = "";
            HttpSession session = request.getSession();
            
            switch(act){
                case LEITURA:
                    Map<Curso, Integer> cursos = cDao.getTodosCursosCountAlunos();
                    session.setAttribute("listaDeCursos", cursos);
                    response.sendRedirect("./relatorios/curso.jsp");
                    break;
                case EXCLUSAO:
                    idCurso = Integer.parseInt(request.getParameter("idCurso"));

                    if (cDao.deleteCurso(idCurso)) {
                        response.sendRedirect("./relatorios/loader.jsp?pagina=curso");
                    } else {
                        
                    }
                    break;
                case CADASTRO:
                    nomeCurso = request.getParameter("nomeCurso");
                    tipoCurso = request.getParameter("tipoCurso");
                    if (cDao.cadastraCurso(new Curso(0, nomeCurso, tipoCurso))) {
                        response.sendRedirect("./relatorios/loader.jsp?pagina=curso");
                    }
                    break;
                case EDICAO:
                    nomeCurso = request.getParameter("nomeCurso");
                    tipoCurso = request.getParameter("tipoCurso");
                    idCurso = Integer.parseInt(request.getParameter("idCurso"));

                    if (cDao.atualizaCurso(idCurso, nomeCurso, tipoCurso)) {
                        response.sendRedirect("./relatorios/loader.jsp?pagina=curso");
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
            Logger.getLogger(CursoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CursoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
