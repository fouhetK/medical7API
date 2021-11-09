package entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "pays", schema = "medical_jpa")
public class PaysEntity {
    private String code;
    private String nom;
    //private Collection<PatientEntity> patientsByCode;
    //private Collection<VilleEntity> villesByCode;

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaysEntity that = (PaysEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        return result;
    }
}
