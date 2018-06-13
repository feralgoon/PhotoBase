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
    private String lensModel;
    private String cameraModel;
    private String iso;
    private String aperture;
    private String exposureTime;
    private Date dateTaken;
    private Date timeTaken;
    private byte[] picture;

    public PhotoDetail(int photoId, String photographer, String lensModel, String cameraModel, String iso, String aperture, String exposureTime, Date dateTaken, Date timeTaken,byte[] picture)
    {
        this.photoId = photoId;
        this.photographer = photographer;
        this.lensModel = lensModel;
        this.cameraModel = cameraModel;
        this.iso = iso;
        this.aperture = aperture;
        this.exposureTime = exposureTime;
        this.dateTaken = dateTaken;
        this.timeTaken = timeTaken;
        this.picture = picture;
    }

    public int getPhotoId()
    {
        return photoId;
    }

    public String getPhotographer()
    {
        return photographer;
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

    public byte[] getPicture()
    {
        return picture;
    }
}
