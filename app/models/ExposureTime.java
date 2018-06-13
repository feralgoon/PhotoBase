package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExposureTime
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int exposureTimeId;

    private String exposureLength;

    public int getExposureTimeId()
    {
        return exposureTimeId;
    }

    public String getExposureLength()
    {

        return exposureLength;
    }

    public void setExposureLength(String exposureLength)
    {
        this.exposureLength = exposureLength;
    }
}
