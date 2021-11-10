package medical.m2i.api;

import entities.PatientEntity;
import entities.PaysEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/patient")
public class PatientRESTAPI {
    private static final EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<PatientEntity> getAll( @QueryParam("nom") String pnom ){
        //List<PatientEntity> p = em.createNativeQuery("SELECT * FROM patient", PatientEntity.class).getResultList();
        List<PatientEntity> p = null;

        System.out.println( "nom passé en param = " + pnom );

        if( pnom == null || pnom.length() == 0  ){
            p = em.createNamedQuery("patient.findAll" ).getResultList();
        }else{
            p = em.createNamedQuery("patient.findAllByNom" ).setParameter("nom" , "%"+pnom+"%").getResultList();
        }

        return p;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PatientEntity getOne(@PathParam("id") int id){
        return getPatient(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void addPatient( PatientEntity p){// Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void updateVille(@PathParam("id") int id, PatientEntity pparam){
        PatientEntity p = getPatient(id);

        p.setNom(pparam.getNom());
        p.setPrenom(pparam.getPrenom());
        p.setAdresse(pparam.getAdresse());
        p.setDatenaissance(pparam.getDatenaissance());
        p.setVilleByVilleId(em.find(VilleEntity.class, pparam.getVilleByVilleId().getId()));
        p.setPaysByPaysCode(em.find(PaysEntity.class, pparam.getPaysByPaysCode().getCode()));

        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();

        // Début des modifications
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    @DELETE
    @Path("/{id}")
    public void deletePatient(@PathParam("id") int id){

        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();

        // Début des modifications
        try {
            tx.begin();
            PatientEntity object = getPatient(id);
            em.remove(em.contains(object) ? object : em.merge(object));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    private PatientEntity getPatient(int id)
    {
        PatientEntity p = em.find(PatientEntity.class, id);
        if (p == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return p;
    }
}
