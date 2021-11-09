package medical.m2i.api;

import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ville")
public class VilleRESTAPI {
    private static final EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<VilleEntity> getAll(){
        return em.createNativeQuery("SELECT * FROM Ville", VilleEntity.class).getResultList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public VilleEntity getOne(@PathParam("id") int id){
        return em.find(VilleEntity.class, id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void addVille( VilleEntity v){// Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(v);
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
    public void deleteVille(@PathParam("id") int id){

        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();

        // Début des modifications
        try {
            tx.begin();
            VilleEntity object = getOne(id);
            em.remove(em.contains(object) ? object : em.merge(object));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}
