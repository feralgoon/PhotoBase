package controllers;

import models.*;
import play.api.Play;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.MetadataExtraction;
import views.html.*;

import javax.inject.Inject;
import javax.persistence.Query;
import java.io.File;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String sql = "SELECT NEW models.PhotoDetail(p.photoId,ph.photographerName,ph.photographerId,lm.lensModelName,cm.cameraModelName,i.isoSpeed,a.apertureName," +
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

    @Transactional(readOnly = true)
    public Result getSearch()
    {
        Map<String,List<String>> searchData = new HashMap<>();

        String sql = "SELECT apertureName FROM Aperture " +
                        "ORDER BY apertureName ASC";
        List<String> apertureList = jpaApi.em().createQuery(sql,String.class).getResultList();
        searchData.put("Aperture",apertureList);

        sql = "SELECT cameraModelName FROM CameraModel " +
                "ORDER BY cameraModelName ASC";
        List<String> cameraModelList = jpaApi.em().createQuery(sql,String.class).getResultList();
        searchData.put("CameraModel",cameraModelList);

        sql = "SELECT exposureLength FROM ExposureTime " +
                "ORDER BY exposureLength ASC";
        List<String> exposureList  = jpaApi.em().createQuery(sql,String.class).getResultList();
        searchData.put("Exposure",exposureList);

        sql = "SELECT isoSpeed FROM Iso " +
                "ORDER BY isoSpeed ASC";
        List<String> isoList = jpaApi.em().createQuery(sql,String.class).getResultList();
        searchData.put("Iso",isoList);

        sql = "SELECT lensModelName FROM LensModel " +
                "ORDER BY lensModelName ASC";
        List<String> lensModelList = jpaApi.em().createQuery(sql,String.class).getResultList();
        searchData.put("LensModel",lensModelList);

        sql = "SELECT photographerName FROM Photographer " +
                "ORDER BY photographerName ASC";
        List<String> photographers = jpaApi.em().createQuery(sql,String.class).getResultList();
        searchData.put("Photographer",photographers);

        return ok(search.render(searchData));
    }

    @Transactional(readOnly = true)
    public Result postSearch()
    {
        List<String> cameramodels;
        List<String> photographers;
        List<String> apertures;
        List<String> exposures;
        List<String> isos;
        List<String> lensmodels;

        if(request().body().asMultipartFormData().asFormUrlEncoded().containsKey("CameraModel"))
        {
            cameramodels = Arrays.asList(request().body().asMultipartFormData().asFormUrlEncoded().get("CameraModel"));
        }
        else
        {
            cameramodels = new ArrayList<>();
        }

        if(request().body().asMultipartFormData().asFormUrlEncoded().containsKey("Photographer"))
        {
            photographers = Arrays.asList(request().body().asMultipartFormData().asFormUrlEncoded().get("Photographer"));
        }
        else
        {
            photographers = new ArrayList<>();
        }

        if(request().body().asMultipartFormData().asFormUrlEncoded().containsKey("Aperture"))
        {
            apertures = Arrays.asList(request().body().asMultipartFormData().asFormUrlEncoded().get("Aperture"));
        }
        else
        {
            apertures = new ArrayList<>();
        }

        if(request().body().asMultipartFormData().asFormUrlEncoded().containsKey("Exposure"))
        {
            exposures = Arrays.asList(request().body().asMultipartFormData().asFormUrlEncoded().get("Exposure"));
        }
        else
        {
            exposures = new ArrayList<>();
        }

        if(request().body().asMultipartFormData().asFormUrlEncoded().containsKey("Iso"))
        {
            isos = Arrays.asList(request().body().asMultipartFormData().asFormUrlEncoded().get("Iso"));
        }
        else
        {
            isos = new ArrayList<>();
        }

        if(request().body().asMultipartFormData().asFormUrlEncoded().containsKey("LensModel"))
        {
            lensmodels = Arrays.asList(request().body().asMultipartFormData().asFormUrlEncoded().get("LensModel"));
        }
        else
        {
            lensmodels = new ArrayList<>();
        }

        String sql = "SELECT NEW models.PhotoDetail(p.photoId,ph.photographerName,ph.photographerId,lm.lensModelName,cm.cameraModelName,i.isoSpeed,a.apertureName," +
                "e.exposureLength,p.dateTaken,p.timeTaken) " +
                "FROM Photo p " +
                "JOIN Photographer ph ON p.photographerId = ph.photographerId " +
                "JOIN LensModel lm ON p.lensModelId = lm.lensModelId " +
                "JOIN CameraModel cm ON p.cameraModelId = cm.cameraModelId " +
                "JOIN Iso i ON p.isoId = i.isoId " +
                "JOIN Aperture a ON p.apertureId = a.apertureId " +
                "JOIN ExposureTime e ON p.exposureTimeId = e.exposureTimeId ";

        int count = 0;

        if(!(cameramodels.isEmpty() || cameramodels.get(0).startsWith("Any")))
        {
            sql += "WHERE cm.cameraModelName IN :cameramodels ";
            count++;
        }

        if(!(photographers.isEmpty() || photographers.get(0).startsWith("Any")))
        {
            if (count != 0)
            {
                sql += "AND ph.photographerName IN :photographers ";
            }
            else
            {
                sql += "WHERE ph.photographerName IN :photographers ";
                count++;
            }
        }

        if(!(apertures.isEmpty() || apertures.get(0).startsWith("Any")))
        {
            if (count != 0)
            {
                sql += "AND a.apertureName IN :apertures ";
            }
            else
            {
                sql += "WHERE a.apertureName IN :apertures ";
                count++;
            }
        }

        if(!(exposures.isEmpty() || exposures.get(0).startsWith("Any")))
        {
            if (count != 0)
            {
                sql += "AND e.exposureLength IN :exposures ";
            }
            else
            {
                sql += "WHERE e.exposureLength IN :exposures ";
                count++;
            }
        }

        if(!(isos.isEmpty() || isos.get(0).startsWith("Any")))
        {
            if (count != 0)
            {
                sql += "AND i.isoSpeed IN :isos ";
            }
            else
            {
                sql += "WHERE i.isoSpeed IN :isos ";
                count++;
            }
        }

        if(!(lensmodels.isEmpty() || lensmodels.get(0).startsWith("Any")))
        {
            if (count != 0)
            {
                sql += "AND lm.lensModelName IN :lensmodels ";
            }
            else
            {
                sql += "WHERE lm.lensModelName IN :lensmodels ";
            }
        }

        Query query = jpaApi.em().createQuery(sql);

        if(!(cameramodels.isEmpty() || cameramodels.get(0).startsWith("Any")))
        {
            query.setParameter("cameramodels",cameramodels);
        }

        if(!(photographers.isEmpty() || photographers.get(0).startsWith("Any")))
        {
            query.setParameter("photographers",photographers);
        }

        if(!(apertures.isEmpty() || apertures.get(0).startsWith("Any")))
        {
            query.setParameter("apertures",apertures);
        }

        if(!(exposures.isEmpty() || exposures.get(0).startsWith("Any")))
        {
            query.setParameter("exposures",exposures);
        }

        if(!(isos.isEmpty() || isos.get(0).startsWith("Any")))
        {
            query.setParameter("isos",isos);
        }

        if(!(lensmodels.isEmpty() || lensmodels.get(0).startsWith("Any")))
        {
            query.setParameter("lensmodels",lensmodels);
        }

        List<PhotoDetail> photoList = query.getResultList();

        return ok(photos.render(photoList));
    }
}

