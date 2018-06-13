package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CameraModel
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int cameraModelId;

    private int manufacturerId;
    private String cameraModelName;

    public int getCameraModelId()
    {
        return cameraModelId;
    }

    public int getManufacturerId()
    {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getCameraModelName()
    {
        return cameraModelName;
    }

    public void setCameraModelName(String cameraModelName)
    {
        this.cameraModelName = cameraModelName;
    }
}
