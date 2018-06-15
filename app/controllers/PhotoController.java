package controllers;

import models.*;
import play.api.Play;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.MetadataExtraction;
import views.html.*;

import javax.inject.Inject;
import java.io.File;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class PhotoController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public PhotoController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    public Result getUpload()
    {
        return ok(upload.render());
    }

    @Transactional(readOnly = true)
    private Photo findPhoto(int photoId)
    {
        String sql = "SELECT p FROM Photo p " +
                     "WHERE p.photoId = :photoId";
        return jpaApi.em().createQuery(sql, Photo.class).setParameter("photoId", photoId).getSingleResult();
    }

    @Transactional(readOnly = true)
    public Result getPhotoPicture(int photoId)
    {
        return ok("/public/images/" + photoId + ".jpg").as("image/jpg");
    }

    @Transactional(readOnly = true)
    public Result getPhotos()
    {
        String sql = "SELECT NEW models.PhotoDetail(p.photoId,ph.photographerName,lm.lensModelName,cm.cameraModelName,i.isoSpeed,a.apertureName," +
                        "e.exposureLength,p.dateTaken,p.timeTaken) " +
                        "FROM Photo p " +
                        "JOIN Photographer ph ON p.photographerId = ph.photographerId " +
                        "JOIN LensModel lm ON p.lensModelId = lm.lensModelId " +
                        "JOIN CameraModel cm ON p.cameraModelId = cm.cameraModelId " +
                        "JOIN Iso i ON p.isoId = i.isoId " +
                        "JOIN Aperture a ON p.apertureId = a.apertureId " +
                        "JOIN ExposureTime e ON p.exposureTimeId = e.exposureTimeId";
        List<PhotoDetail> photoList = jpaApi.em().createQuery(sql, PhotoDetail.class).getResultList();

        return ok(photos.render(photoList));
    }

    @Transactional
    public Result postUpload() throws ParseException
    {
        Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart = formData.getFile("photo");
        File file = filePart.getFile();

        MetadataExtraction metadataExtraction = new MetadataExtraction();
        Map<String, String> exifData = metadataExtraction.getMetadata(file);
        System.out.println("map size : " + exifData.size());

        for (String s : exifData.keySet())
        {
            System.out.println(s + ": " + exifData.get(s));
        }

        Photo photo = new Photo();
        System.out.println(file);

        if (exifData.size() == 8)
        {
            Aperture aperture;
            String sql = "SELECT a FROM Aperture a " +
                            "WHERE ApertureName = :currentAperture";
            List<Aperture> apertureList = jpaApi.em().createQuery(sql, Aperture.class).setParameter("currentAperture", exifData.get("F-Number")).getResultList();
            if (apertureList == null || apertureList.isEmpty())
            {
                aperture = new Aperture();
                aperture.setApertureName(exifData.get("F-Number"));
                jpaApi.em().persist(aperture);
            }
            else
            {
                aperture = apertureList.get(0);
            }

            Manufacturer manufacturer;
            sql = "SELECT m FROM Manufacturer m " +
                    "WHERE ManufacturerName = :manufacturer";
            List<Manufacturer> manufacturerList = jpaApi.em().createQuery(sql, Manufacturer.class).setParameter("manufacturer", exifData.get("Make")).getResultList();
            if (manufacturerList == null || manufacturerList.isEmpty())
            {
                manufacturer = new Manufacturer();
                manufacturer.setManufacturerName(exifData.get("Make"));
                jpaApi.em().persist(manufacturer);
            }
            else
            {
                manufacturer = manufacturerList.get(0);
            }

            CameraModel cameraModel;
            sql = "SELECT c FROM CameraModel c " +
                    "WHERE CameraModelName = :currentCameraModel";
            List<CameraModel> cameraModelList = jpaApi.em().createQuery(sql, CameraModel.class).setParameter("currentCameraModel", exifData.get("Model")).getResultList();
            if (cameraModelList == null || cameraModelList.isEmpty())
            {
                cameraModel = new CameraModel();
                cameraModel.setCameraModelName(exifData.get("Model"));
                cameraModel.setManufacturerId(manufacturer.getManufacturerId());
                jpaApi.em().persist(cameraModel);
            }
            else
            {
                cameraModel = cameraModelList.get(0);
            }

            ExposureTime exposure;
            sql = "SELECT e FROM ExposureTime e " +
                    "WHERE ExposureLength = :currentExposure";
            List<ExposureTime> exposureTimeList = jpaApi.em().createQuery(sql, ExposureTime.class).setParameter("currentExposure", exifData.get("Exposure Time")).getResultList();
            if (exposureTimeList == null || exposureTimeList.isEmpty())
            {
                exposure = new ExposureTime();
                exposure.setExposureLength(exifData.get("Exposure Time"));
                jpaApi.em().persist(exposure);
            }
            else
            {
                exposure = exposureTimeList.get(0);
            }

            Iso iso;
            sql = "SELECT i FROM Iso i " +
                    "WHERE IsoSpeed = :currentIso";
            List<Iso> isoList = jpaApi.em().createQuery(sql, Iso.class).setParameter("currentIso", exifData.get("ISO Speed Ratings")).getResultList();
            if (isoList == null || isoList.isEmpty())
            {
                iso = new Iso();
                iso.setIsoSpeed(exifData.get("ISO Speed Ratings"));
                jpaApi.em().persist(iso);
            }
            else
            {
                iso = isoList.get(0);
            }

            LensModel lensModel;
            sql = "SELECT l FROM LensModel l " +
                    "WHERE LensModelName = :currentLens";
            List<LensModel> lensModelList = jpaApi.em().createQuery(sql, LensModel.class).setParameter("currentLens", exifData.get("Lens Model")).getResultList();
            if (lensModelList == null || lensModelList.isEmpty())
            {
                lensModel = new LensModel();
                lensModel.setLensModelName(exifData.get("Lens Model"));
                jpaApi.em().persist(lensModel);
            }
            else
            {
                lensModel = lensModelList.get(0);
            }

            Photographer photographer;
            sql = "SELECT p FROM Photographer p " +
                    "WHERE PhotographerName = :currentPhotographer";
            List<Photographer> photographerList = jpaApi.em().createQuery(sql, Photographer.class).setParameter("currentPhotographer", exifData.get("Artist")).getResultList();
            if (photographerList == null || photographerList.isEmpty())
            {
                photographer = new Photographer();
                photographer.setPhotographerName(exifData.get("Artist"));
                jpaApi.em().persist(photographer);
            }
            else
            {
                photographer = photographerList.get(0);
            }

            Date date;
            Time time;

            String[] dateTime = exifData.get("Date/Time Original").split(" ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            java.util.Date tempDate = dateFormat.parse(dateTime[0]);
            java.util.Date tempTime = timeFormat.parse(dateTime[1]);

            date = new Date(tempDate.getTime());
            time = new Time(tempTime.getTime());

            photo.setDateTaken(date);
            photo.setTimeTaken(time);
            photo.setApertureId(aperture.getApertureId());
            photo.setCameraModelId(cameraModel.getCameraModelId());
            photo.setExposureTimeId(exposure.getExposureTimeId());
            photo.setIsoId(iso.getIsoId());
            photo.setLensModelId(lensModel.getLensModelId());
            photo.setPhotographerId(photographer.getPhotographerId());
            jpaApi.em().persist(photo);


            System.out.println(Play.current().path().getAbsolutePath());
            System.out.println(file.renameTo(new File(Play.current().path().getAbsolutePath() + "/public/images/" + photo.getPhotoId() + ".jpg")));
            System.out.println(file.delete());
        }

        return ok(upload.render());
    }
}

