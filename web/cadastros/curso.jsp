<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html;charset=UTF-8");
    request.setCharacterEncoding("UTF-8");

    String idCurso = "";
    String nomeCurso = "";
    String tipoCurso = "";
    String acao = "CADASTRO";
    String txtBotao = "Cadastrar";

    if (request.getParameter("idCurso") != null) {
        nomeCurso = request.getParameter("curso");
        tipoCurso = request.getParameter("tipo");
        idCurso = request.getParameter("idCurso");
        acao = "EDICAO";
        txtBotao = "Alterar curso";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="../menu.jsp"></jsp:include>
            <div class="container mt-5 pt-5">
                <div style="width: 40%; margin: 0 auto;">
                    <form method="post" action="../CursoController">
                        <div class="form-goup">
                            <label>Nome do Curso:</label>
                            <input type="text" class="form-control" required name="nomeCurso" maxlength="90" value="<%=nomeCurso%>">
                        </div>

                        <div class="form-goup mt-4">
                            <label>Tipo do Curso:</label>
                            <input type="text" class="form-control" required name="tipoCurso" maxlength="50" value="<%=tipoCurso%>">
                        </div>

                        <div class="mt-4">
                            <input type="submit" class="btn btn-primary btn-md w-100" value="<%=txtBotao%>">
                        </div>

                        <input type="hidden" name="acao" value="<%=acao%>">
                        <input type="hidden" name="idCurso" value="<%=idCurso%>">
                </form>
            </div>
        </div>
    </body>
</html>
