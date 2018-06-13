package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LensModel
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int lensModelId;

    private String lensModelName;

    public int getLensModelId()
    {
        return lensModelId;
    }

    public String getLensModelName()
    {
        return lensModelName;
    }

    public void setLensModelName(String lensModelName)
    {
        this.lensModelName = lensModelName;
    }
}
