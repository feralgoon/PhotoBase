package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Iso
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int isoId;

    private String isoSpeed;

    public int getIsoId()
    {
        return isoId;
    }

    public String getIsoSpeed()
    {
        return isoSpeed;
    }

    public void setIsoSpeed(String isoSpeed)
    {
        this.isoSpeed = isoSpeed;
    }
}
