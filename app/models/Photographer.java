package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Photographer
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int photographerId;

    private String photographerName;

    public int getPhotographerId()
    {
        return photographerId;
    }

    public String getPhotographer()
    {
        return photographerName;
    }

    public void setPhotographerName(String photographerName)
    {
        this.photographerName = photographerName;
    }
}
