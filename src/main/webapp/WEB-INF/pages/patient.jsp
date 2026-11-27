
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste Patient</title>
</head>
<body>

    <h1>Liste des patients</h1>
    <c:if test="${not empty message}">
        <p><c:out value="${message}" /></p>
    </c:if>

    <p><a href="${pageContext.request.contextPath}/patients/nouveau">Ajouter un patient</a></p>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Date de naissance</th>
            <th>Lieu de naissance</th>
            <th>Adresse</th>
            <th>Sexe</th>
            <th>Actions</th>
        </tr>

        <c:forEach var="patient" items="${patients}">
            <tr>
                <td><c:out value="${patient.id}" /></td>
                <td><c:out value="${patient.nom}" /></td>
                <td><c:out value="${patient.date_naissance}" /></td>
                <td><c:out value="${patient.lieu_naissance}" /></td>
                <td><c:out value="${patient.adresse}" /></td>
                <td><c:out value="${patient.sexe}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/patients/modifier?id=${patient.id}">Modifier</a>
                    <form method="post" action="${pageContext.request.contextPath}/patients/supprimer"
                          onsubmit="return confirm('Supprimer ce patient ?');">
                        <input type="hidden" name="id" value="${patient.id}">
                        <button type="submit">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>
