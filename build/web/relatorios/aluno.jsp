<%@page import="br.sisacademico.model.Aluno"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html;charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    
    ArrayList<Aluno> alunos = (ArrayList) session.getAttribute("listaDeAlunos");
    boolean mostrarFiltro = request.getParameter("idCurso") != null ? true : false;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="../menu.jsp"></jsp:include>
        <div class="container mt-5">
            <div class="table-responsive-md" style="width: 90%; margin: 0 auto;">
                <table class="table justify-content-center">
                    <thead class="thead-dark">
                    <th scope="col">RA</th>
                    <th scope="col">Nome do Aluno</th>
                    <th scope="col">Curso</th>
                    <th scope="col">Tipo de Curso</th>
                    <th scope="col" class="text-center">Editar</th>
                    <th scope="col" class="text-center">Excluir</th>
                    </thead>
                    <tbody>
                        <% for (Aluno a : alunos) {%>
                        <tr>
                            <td><%=a.getRa()%></td>
                            <td><%= a.getNome()%></td>
                            <td><%= a.getCurso().getNomeCurso()%></td>
                            <td><%= a.getCurso().getTipoCurso()%></td>
                            
                            <%
                                String editParams = String.format("&idAluno=%s&nome=%s&ra=%s&idCurso=%s", 
                                        a.getIdAluno(), a.getNome(), a.getRa(), a.getCurso().getIdCurso());
                            %>
                            
                            <td class="text-center"><a class="btn btn-outline-primary" href="<%=request.getContextPath()%>/AlunoController?acao=CARREGAMENTO<%=editParams%>">Editar</a></td>

                            
                            <% if (mostrarFiltro) {%>
                            <td class="text-center"><a class="btn btn-outline-danger" id="deleteAluno" 
                                                       href="../AlunoController?acao=EXCLUSAO&idCurso=<%=a.getCurso().getIdCurso()%>&idAluno=<%=a.getIdAluno()%>">Excluir</a></td>
                                <% } else {%>
                            <td class="text-center"><a class="btn btn-outline-danger" id="deleteAluno" 
                                                       href="../AlunoController?acao=EXCLUSAO&idAluno=<%=a.getIdAluno()%>">Excluir</a></td>
                                <% } %>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>