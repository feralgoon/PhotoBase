package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Manufacturer
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int manufacturerId;

    private String manufacturerName;

    public int getManufacturerId()
    {
        return manufacturerId;
    }

    public String getManufacturerName()
    {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName)
    {
        this.manufacturerName = manufacturerName;
    }
}
