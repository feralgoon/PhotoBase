package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PhotoDetail
{
    @Id
    private int photoId;

    private String photographer;
    private int photographerId;
    private String lensModel;
    private String cameraModel;
    private String iso;
    private String aperture;
    private String exposureTime;
    private Date dateTaken;
    private Date timeTaken;

    public PhotoDetail(int photoId, String photographer, int photographerId,String lensModel, String cameraModel, String iso, String aperture, String exposureTime, Date dateTaken, Date timeTaken)
    {
        this.photoId = photoId;
        this.photographer = photographer;
        this.photographerId = photographerId;
        this.lensModel = lensModel;
        this.cameraModel = cameraModel;
        this.iso = iso;
        this.aperture = aperture;
        this.exposureTime = exposureTime;
        this.dateTaken = dateTaken;
        this.timeTaken = timeTaken;

    }

    public int getPhotoId()
    {
        return photoId;
    }

    public String getPhotographer()
    {
        return photographer;
    }

    public int getPhotographerId()
    {
        return photographerId;
    }

    public String getLensModel()
    {
        return lensModel;
    }

    public String getCameraModel()
    {
        return cameraModel;
    }

    public String getIso()
    {
        return iso;
    }

    public String getAperture()
    {
        return aperture;
    }

    public String getExposureTime()
    {
        return exposureTime;
    }

    public Date getDateTaken()
    {
        return dateTaken;
    }

    public Date getTimeTaken()
    {
        return timeTaken;
    }

}
