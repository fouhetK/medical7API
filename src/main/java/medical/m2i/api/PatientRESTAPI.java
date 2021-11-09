package medical.m2i.api;

import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patient")
public class PatientRESTAPI {
    private static final EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<PatientEntity> getAll(){
        List<PatientEntity> p = em.createNativeQuery("SELECT * FROM Patient", PatientEntity.class).getResultList();
        return p;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PatientEntity getOne(@PathParam("id") int id){
        return em.find(PatientEntity.class, id);
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
        } finally {
            // em.close();
            // emf.close();
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
            PatientEntity object = getOne(id);
            em.remove(em.contains(object) ? object : em.merge(object));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}
