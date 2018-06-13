package controllers;


import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.people.PeopleInterface;
import models.Photo;
import models.PhotoDetail;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;

import services.FlickrAPICall;
import services.MetadataExtraction;
import views.html.*;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController extends Controller
{
    JPAApi jpaApi;

    @Inject
    public HomeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result index()
    {
        /*FlickrAPICall flickrAPICall = new FlickrAPICall();
        Map<Photo,Map<String,String>> finalPhotoMap = flickrAPICall.getPhotoMap();
        PeopleInterface peopleInterface = flickrAPICall.getPeopleInterface();
        return ok(index.render(finalPhotoMap,peopleInterface));
        */
        MetadataExtraction metadataExtraction = new MetadataExtraction();
        File folder = new File("C:/Users/Joseph/Desktop/ProjectPhotos/");
        //File folder = new File("C:/Users/Joseph/Desktop/mirflickr/");

        File[] fileList = folder.listFiles();
        Map<String,Map<String,String>> photosWithData = new HashMap<>();


        return ok(index.render(photosWithData));
    }



}
