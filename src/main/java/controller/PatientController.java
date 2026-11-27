package controller;

import main.java.itu.annotation.Controller;
import main.java.itu.annotation.UrlMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PatientModel;
import repository.PatientRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import main.java.core.ModelAndView;


@Controller 
public class PatientController {

    @UrlMapping(url="/patient", methodeHttp="GET")
    public ModelAndView patient() throws SQLException {
        return afficherPatients();
    }
    
    @UrlMapping (url="/patients", methodeHttp="GET")
    public ModelAndView listePatient() throws SQLException {
        return afficherPatients();
    }

    @UrlMapping(url="/patients/nouveau", methodeHttp="GET")
    public ModelAndView nouveauPatient() throws SQLException {
        return formulairePatient(new PatientModel(), null);
    }

    @UrlMapping(url="/patients/modifier", methodeHttp="GET")
    public ModelAndView modifierPatient(HttpServletRequest request) throws SQLException {
        Integer id = lireId(request.getParameter("id"));
        if (id == null) {
            return afficherPatients("Identifiant de patient invalide.");
        }

        PatientModel patient = new PatientRepository().findById(id);
        if (patient == null) {
            return afficherPatients("Patient introuvable.");
        }
        return formulairePatient(patient, null);
    }

    @UrlMapping(url="/patients/creer", methodeHttp="GET")
    public ModelAndView creerPatient(HttpServletRequest request,
                                     HttpServletResponse response) throws SQLException {
        if (!estPost(request, response)) {
            return afficherPatients("La création doit être envoyée avec un formulaire.");
        }

        PatientModel patient = lirePatient(request);
        String erreur = valider(patient);
        if (erreur != null) {
            return formulairePatient(patient, erreur);
        }

        new PatientRepository().create(patient);
        return afficherPatients("Patient ajouté avec succès.");
    }

    @UrlMapping(url="/patients/enregistrer", methodeHttp="GET")
    public ModelAndView enregistrerModification(HttpServletRequest request,
                                                HttpServletResponse response)
            throws SQLException {
        if (!estPost(request, response)) {
            return afficherPatients("La modification doit être envoyée avec un formulaire.");
        }

        Integer id = lireId(request.getParameter("id"));
        if (id == null) {
            return afficherPatients("Identifiant de patient invalide.");
        }

        PatientModel patient = lirePatient(request);
        patient.setId(id);
        String erreur = valider(patient);
        if (erreur != null) {
            return formulairePatient(patient, erreur);
        }

        if (!new PatientRepository().update(patient)) {
            return afficherPatients("Patient introuvable.");
        }
        return afficherPatients("Patient modifié avec succès.");
    }

    @UrlMapping(url="/patients/supprimer", methodeHttp="GET")
    public ModelAndView supprimerPatient(HttpServletRequest request,
                                         HttpServletResponse response) throws SQLException {
        if (!estPost(request, response)) {
            return afficherPatients("La suppression doit être envoyée avec un formulaire.");
        }

        Integer id = lireId(request.getParameter("id"));
        if (id == null) {
            return afficherPatients("Identifiant de patient invalide.");
        }
        if (!new PatientRepository().delete(id)) {
            return afficherPatients("Patient introuvable.");
        }
        return afficherPatients("Patient supprimé avec succès.");
    }

    private ModelAndView afficherPatients() throws SQLException {
        return afficherPatients(null);
    }

    private ModelAndView afficherPatients(String message) throws SQLException {
        PatientRepository repository = new PatientRepository();
        List<PatientModel> patients = repository.findAll();
        ModelAndView mv = new ModelAndView("patient");
        mv.addAttribue("patients", patients);
        mv.addAttribue("message", message);
        return mv;
    }

    private ModelAndView formulairePatient(PatientModel patient, String erreur)
            throws SQLException {
        ModelAndView mv = new ModelAndView("patient-form");
        Map<Integer, String> sexes = new PatientRepository().findAllSexes();
        mv.addAttribue("patient", patient);
        mv.addAttribue("sexes", sexes);
        mv.addAttribue("erreur", erreur);
        return mv;
    }

    private PatientModel lirePatient(HttpServletRequest request) {
        PatientModel patient = new PatientModel();
        patient.setNom(request.getParameter("nom"));
        patient.setLieu_naissance(request.getParameter("lieu_naissance"));
        patient.setAdresse(request.getParameter("adresse"));

        String date = request.getParameter("date_naissance");
        if (date != null && !date.isBlank()) {
            try {
                patient.setDate_naissance(LocalDate.parse(date));
            } catch (DateTimeParseException ignored) {
                // La validation signale ensuite le format incorrect.
            }
        }

        Integer idSexe = lireId(request.getParameter("id_sexe"));
        if (idSexe != null) {
            patient.setId_sexe(idSexe);
        }
        return patient;
    }

    private String valider(PatientModel patient) throws SQLException {
        if (patient.getNom() == null || patient.getNom().isBlank()
                || patient.getDate_naissance() == null
                || patient.getLieu_naissance() == null
                || patient.getLieu_naissance().isBlank()
                || patient.getAdresse() == null
                || patient.getAdresse().isBlank()
                || patient.getId_sexe() <= 0) {
            return "Tous les champs sont obligatoires et doivent être valides.";
        }
        if (!new PatientRepository().findAllSexes().containsKey(patient.getId_sexe())) {
            return "Le sexe sélectionné n'existe pas.";
        }
        return null;
    }

    private Integer lireId(String valeur) {
        if (valeur == null) {
            return null;
        }
        try {
            int id = Integer.parseInt(valeur);
            return id > 0 ? id : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private boolean estPost(HttpServletRequest request, HttpServletResponse response) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.setHeader("Allow", "POST");
        return false;
    }
}
