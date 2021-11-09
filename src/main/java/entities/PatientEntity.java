package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "patient", schema = "medical_jpa")
public class PatientEntity {
    private int id;
    private String adresse;
    private Date datenaissance;
    private String nom;
    private String prenom;
    private PaysEntity paysByPaysCode;
    private VilleEntity villeByVilleId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "adresse")
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "datenaissance")
    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientEntity that = (PatientEntity) o;

        if (id != that.id) return false;
        if (adresse != null ? !adresse.equals(that.adresse) : that.adresse != null) return false;
        if (datenaissance != null ? !datenaissance.equals(that.datenaissance) : that.datenaissance != null)
            return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        result = 31 * result + (datenaissance != null ? datenaissance.hashCode() : 0);
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "pays_code", referencedColumnName = "code")
    public PaysEntity getPaysByPaysCode() {
        return paysByPaysCode;
    }

    public void setPaysByPaysCode(PaysEntity paysByPaysCode) {
        this.paysByPaysCode = paysByPaysCode;
    }

    @ManyToOne
    @JoinColumn(name = "ville_id", referencedColumnName = "id")
    public VilleEntity getVilleByVilleId() {
        return villeByVilleId;
    }

    public void setVilleByVilleId(VilleEntity villeByVilleId) {
        this.villeByVilleId = villeByVilleId;
    }
}
