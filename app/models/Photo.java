package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;


@Entity
public class Photo
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int photoId;

    private int photographerId;
    private int lensModelId;
    private int cameraModelId;
    private int isoId;
    private int apertureId;
    private int exposureTimeId;
    private Date dateTaken;
    private Time timeTaken;

    public int getPhotoId()
    {
        return photoId;
    }

    public int getPhotographerId()
    {
        return photographerId;
    }

    public void setPhotographerId(int photographerId)
    {
        this.photographerId = photographerId;
    }

    public int getLensModelId()
    {
        return lensModelId;
    }

    public void setLensModelId(int lensModelId)
    {
        this.lensModelId = lensModelId;
    }

    public int getCameraModelId()
    {
        return cameraModelId;
    }

    public void setCameraModelId(int cameraModelId)
    {
        this.cameraModelId = cameraModelId;
    }

    public int getIsoId()
    {
        return isoId;
    }

    public void setIsoId(int isoId)
    {
        this.isoId = isoId;
    }

    public int getApertureId()
    {
        return apertureId;
    }

    public void setApertureId(int apertureId)
    {
        this.apertureId = apertureId;
    }

    public int getExposureTimeId()
    {
        return exposureTimeId;
    }

    public void setExposureTimeId(int exposureTimeId)
    {
        this.exposureTimeId = exposureTimeId;
    }


    public Date getDateTaken()
    {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken)
    {
        this.dateTaken = dateTaken;
    }

    public Time getTimeTaken()
    {
        return timeTaken;
    }

    public void setTimeTaken(Time timeTaken)
    {
        this.timeTaken = timeTaken;
    }

}
