package controllers;

import models.Photo;
import models.PhotoDetail;
import models.Photographer;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;
import java.util.List;

public class PhotographerController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public PhotographerController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getPhotographers()
    {
        String sql = "SELECT p FROM Photographer p";

        List<Photographer> photographerList = jpaApi.em().createQuery(sql,Photographer.class).getResultList();

        return ok(photographers.render(photographerList));
    }

    @Transactional(readOnly = true)
    public Result getPhotographer(int photoId)
    {
        String sql = "SELECT p FROM Photo p " +
                "WHERE PhotoId = :photoId";
        Photo photo = jpaApi.em().createQuery(sql,Photo.class).setParameter("photoId",photoId).getSingleResult();

        sql = "SELECT NEW models.PhotoDetail(p.photoId,ph.photographerName,lm.lensModelName,cm.cameraModelName,i.isoSpeed,a.apertureName," +
                "e.exposureLength,p.dateTaken,p.timeTaken) " +
                "FROM Photo p " +
                "JOIN Photographer ph ON p.photographerId = ph.photographerId " +
                "JOIN LensModel lm ON p.lensModelId = lm.lensModelId " +
                "JOIN CameraModel cm ON p.cameraModelId = cm.cameraModelId " +
                "JOIN Iso i ON p.isoId = i.isoId " +
                "JOIN Aperture a ON p.apertureId = a.apertureId " +
                "JOIN ExposureTime e ON p.exposureTimeId = e.exposureTimeId " +
                "WHERE p.photographerId = :photographerId";
        List<PhotoDetail> photoList = jpaApi.em().createQuery(sql, PhotoDetail.class).setParameter("photographerId",photo.getPhotographerId()).getResultList();

        return ok(photographer.render(photoList));
    }



}
