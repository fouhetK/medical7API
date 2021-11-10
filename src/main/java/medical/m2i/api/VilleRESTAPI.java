package medical.m2i.api;

import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ville")
public class VilleRESTAPI {
    private static final EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<VilleEntity> getAll( @QueryParam("nom") String pnom ){
        //List<PatientEntity> p = em.createNativeQuery("SELECT * FROM patient", PatientEntity.class).getResultList();
        List<VilleEntity> v = null;

        System.out.println( "nom passé en param = " + pnom );

        if( pnom == null || pnom.length() == 0  ){
            v = em.createNamedQuery("ville.findAll" ).getResultList();
        }else{
            v = em.createNamedQuery("ville.findAllByNom" ).setParameter("nom" , "%"+pnom+"%").getResultList();
        }

        return v;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public VilleEntity getOne(@PathParam("id") int id){
        return getVille(id);
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
            throw e;
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
            VilleEntity object = getVille(id);
            em.remove(em.contains(object) ? object : em.merge(object));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void updateVille(@PathParam("id") int id, VilleEntity vparam){
        VilleEntity v = getVille(id);

        v.setNom(vparam.getNom());
        v.setCodePostal(vparam.getCodePostal());
        v.setPaysByPaysCode(vparam.getPaysByPaysCode());

        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();

        // Début des modifications
        try {
            tx.begin();
            em.merge(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    private VilleEntity getVille(int id)
    {
        VilleEntity v = em.find(VilleEntity.class, id);
        if (v == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return v;
    }
}
