<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><c:choose><c:when test="${patient.id > 0}">Modifier</c:when><c:otherwise>Ajouter</c:otherwise></c:choose> un patient</title>
</head>
<body>
    <h1>
        <c:choose>
            <c:when test="${patient.id > 0}">Modifier un patient</c:when>
            <c:otherwise>Ajouter un patient</c:otherwise>
        </c:choose>
    </h1>

    <c:if test="${not empty erreur}">
        <p role="alert"><c:out value="${erreur}" /></p>
    </c:if>

    <c:choose>
        <c:when test="${patient.id > 0}">
            <c:url var="formAction" value="/patients/enregistrer" />
        </c:when>
        <c:otherwise>
            <c:url var="formAction" value="/patients/creer" />
        </c:otherwise>
    </c:choose>
    <form method="post" action="${formAction}">
        <c:if test="${patient.id > 0}">
            <input type="hidden" name="id" value="${patient.id}">
        </c:if>
        <label for="nom">Nom</label>
        <input id="nom" name="nom" type="text" maxlength="100" required
               value="<c:out value='${patient.nom}' />">

        <label for="date_naissance">Date de naissance</label>
        <input id="date_naissance" name="date_naissance" type="date" required
               value="${patient.date_naissance}">

        <label for="lieu_naissance">Lieu de naissance</label>
        <input id="lieu_naissance" name="lieu_naissance" type="text" maxlength="100" required
               value="<c:out value='${patient.lieu_naissance}' />">

        <label for="adresse">Adresse</label>
        <input id="adresse" name="adresse" type="text" maxlength="50" required
               value="<c:out value='${patient.adresse}' />">

        <label for="id_sexe">Sexe</label>
        <select id="id_sexe" name="id_sexe" required>
            <option value="">Choisir</option>
            <c:forEach var="sexe" items="${sexes}">
                <option value="${sexe.key}"
                    <c:if test="${patient.id_sexe == sexe.key}">selected</c:if>>
                    <c:out value="${sexe.value}" />
                </option>
            </c:forEach>
        </select>

        <button type="submit">
            <c:choose>
                <c:when test="${patient.id > 0}">Enregistrer</c:when>
                <c:otherwise>Ajouter</c:otherwise>
            </c:choose>
        </button>
    </form>

    <p><a href="${pageContext.request.contextPath}/patients">Retour à la liste</a></p>
</body>
</html>
