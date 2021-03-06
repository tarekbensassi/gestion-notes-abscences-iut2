package model;

import java.util.ArrayList;
import java.util.List;

import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

/**
 * Tableau absence
 *
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Thu Jan 10 11:37:58 CET 2013]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class AbsencePeer
    extends model.BaseAbsencePeer
{
    /** Serial version */
    private static final long serialVersionUID = 1357814278388L;
    /**
     * Return list of Absences for a etudiantId sent
     *
     * @return List<Absence>
     */
	public static List<Absence> getAbsencesEtudiant(int etudiantId) throws TorqueException
    {
		Criteria crit = new Criteria();
		crit.add(ETUDIANT_ID, etudiantId);
    	doSelect(crit);
		
		return doSelect(crit);

    }
    
    /**
     * Return list of Absences for a etudiantId and matiereId sent
     *
     * @return List<Absence>
     */
	public static List<Absence> getAbsencesEtudiantForMatiere(int etudiantId, int matiereId) throws TorqueException
    {
		Criteria crit = new Criteria();
		crit.add(ETUDIANT_ID, etudiantId);
		
		crit.and(MATIERE_ID, matiereId);
		
		return doSelect(crit);
    }
    
    /**
     * Return list of Absences for a groupeId sent
     *
     * @return List<Absence>
     */
	public static List<Absence> getAbsencesGroupe(int groupeId) throws TorqueException
    {
		Criteria crit = new Criteria();
		crit.addJoin(EtudiantPeer.GROUPE_ID, GroupePeer.ID);
		crit.add(GroupePeer.ID, groupeId);
				
		return doSelectJoinEtudiant(crit);
    }
    
    /**
     * Return list of Absences for a groupeId and matiereId sent
     *
     * @return List<Absence>
     */
	public static List<Absence> getAbsencesGroupeForMatiere(int groupeId, int matiereId) throws TorqueException
    {
		Criteria crit = new Criteria();
		crit.add(MATIERE_ID, matiereId);
		crit.addJoin(EtudiantPeer.GROUPE_ID, GroupePeer.ID);
		crit.add(GroupePeer.ID, groupeId);
				
		return doSelectJoinEtudiant(crit);
    }
}
