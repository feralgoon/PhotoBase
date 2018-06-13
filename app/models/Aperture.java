package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Aperture
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int apertureId;

    private String apertureName;

    public int getApertureId()
    {
        return apertureId;
    }

    public String getApertureName()
    {
        return apertureName;
    }

    public void setApertureName(String apertureName)
    {
        this.apertureName = apertureName;
    }
}
