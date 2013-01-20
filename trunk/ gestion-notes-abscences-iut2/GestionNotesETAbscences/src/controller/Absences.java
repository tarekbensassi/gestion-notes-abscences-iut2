package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.torque.NoRowsException;
import org.apache.torque.TooManyRowsException;
import org.apache.torque.TorqueException;

import model.Absence;
import model.AbsencePeer;
import model.Etudiant;
import model.EtudiantPeer;
import model.Groupe;
import model.GroupePeer;
import model.Matiere;
import model.MatierePeer;




public class Absences{

	private Controleur controleur;

	public Absences(Controleur controleur) {
		// TODO Auto-generated constructor stub
		this.controleur=controleur;

	}

	public  void traiterActions(HttpServletRequest request, HttpServletResponse response, String action, String methode) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String act=action;
		System.out.println("action : "+act);

		if(act.indexOf("/")>0){
			//Répartition dans les controleurs
			act = act.substring(act.indexOf("/")+1);
			System.out.println(methode.equals("get")&& act.equals("editer"));
			if (methode.equals("get") && act.equals("editer")) {
				editer(request, response);
			
			} else if (methode.equals("get") && act.equals("supprimer")) {
				supprimer(request, response);
				
			} else if (methode.equals("get") && act.equals("creer")) {
				creer(request, response);
			
			}
		}else{
			home(request, response);	
		}
		System.out.println("action : "+act);

		
	
}
	
	//
	private  void home(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pathView",controleur.getPathAbsences());
		System.out.println(request.getParameter("choix"));
		// on récupère les données du post
		String choix = "groupe";//request.getParameter("choix");
		int choixMatiere = 0;//Integer.parseInt(request.getParameter("matiere"));
		
		List<Etudiant> listEtudiants;
		List<Groupe> listGroupes;
		List<Matiere> listMatiere;
		
		try {
			/*************Remplissage du formulaire***************/
			//Accès aux données
			listEtudiants = EtudiantPeer.doSelectAll();
			listGroupes = GroupePeer.doSelectAll();
			listMatiere = MatierePeer.doSelectAll();
			
			//Transferer les listes à la VUE
			request.setAttribute("etudiants",listEtudiants);
			request.setAttribute("groupes",listGroupes);
			request.setAttribute("matieres",listMatiere);

			/*************Remplissage du tableau******************/
	    	List<Absence> absences = new ArrayList<Absence>();
			if(choix=="etudiant"){
				int choixEtudiant = 1;//Integer.parseInt(request.getParameter("etudiant"));
				if(choixEtudiant!=0){
					if(choixMatiere!=0){
						absences = Absence.getAbsencesEtudiantForMatiere(choixEtudiant, choixMatiere);
					}else{
						absences = Absence.getAbsencesEtudiant(choixEtudiant);
					}
				}
			}else{
				int choixGroupe = 1;//Integer.parseInt(request.getParameter("groupe"));
				if(choixGroupe!=0){
					if(choixMatiere!=0){
						absences = Absence.getAbsencesGroupeForMatiere(choixGroupe, choixMatiere);
					}else{
						absences = Absence.getAbsencesGroupe(choixGroupe);
					}
				}
			}
			//Transferer les notes
			request.setAttribute("absences",absences);
			
			/*************Remplissage de la moyenne******************/
			
			int nbAbs = 0;
			int nbHeures = 0;
			for(Absence absence : absences){
				nbAbs = nbAbs + 1;
				nbHeures = nbHeures + absence.getNbheures();
			}
			
			//Transferer la moyenne des notes
			request.setAttribute("nbAbs",nbAbs);
			request.setAttribute("nbHeures",nbHeures);
			
			controleur.loadJSP(controleur.getPathMain(), request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//
	private  void supprimer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pathView",controleur.getPathSupprimerAbsences());
		controleur.loadJSP(controleur.getPathMain(), request, response);

	}
	
	//
	private  void creer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pathView",controleur.getPathCreerAbsences());
		controleur.loadJSP(controleur.getPathMain(), request, response);
		try {
		int id= Integer.parseInt(request.getParameter("id"));
		int nbheures=Integer.parseInt(request.getParameter("nbheures"));
		int etudiantId =Integer.parseInt(request.getParameter("etudiantId"));
		int matiereId=Integer.parseInt(request.getParameter("matiereId"));
		Date date=new Date(request.getParameter("date"));
		Absence abs =AbsencePeer.retrieveByPK(id);
		abs.setNbheures(nbheures);
		
		abs.setEtudiant(EtudiantPeer.retrieveByPK(etudiantId));
		abs.setMatiere(MatierePeer.retrieveByPK(matiereId));
		} catch (NoRowsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyRowsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TorqueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private  void editer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			
			//Accès aux données
			List<Etudiant> listEtudiants;
			List<Matiere> listMatieres;

			listEtudiants = EtudiantPeer.doSelectAll();
			listMatieres = MatierePeer.doSelectAll();
			
			Absence abs =AbsencePeer.retrieveByPK(Integer.parseInt(request.getParameter("id")));

			//Transferer les listes à la VUE
			request.setAttribute("etudiants",listEtudiants);
			
			request.setAttribute("pathView",controleur.getPathEditerAbsences());
			request.setAttribute("absence",abs);
			request.setAttribute("titre","Editer une absence de "+abs.getEtudiant().getPrenom()+" "+abs.getEtudiant().getNom());
			request.setAttribute("actionForm","modifier");

			request.setAttribute("etudiants",listEtudiants);
			request.setAttribute("Matieres",listMatieres);

			System.out.println(abs);
			controleur.loadJSP(controleur.getPathMain(), request, response);

		
		} catch (NoRowsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyRowsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TorqueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}