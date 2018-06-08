package controllers;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Exif;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.tags.Tag;
import play.mvc.*;

import services.FlickrService;
import views.html.*;

import java.util.*;
import java.util.stream.Collectors;

public class HomeController extends Controller
{
    public Result index() throws FlickrException
    {
        FlickrService flickrService = new FlickrService();
        Flickr f = flickrService.getFlickr();

        PhotosInterface photosInterface = f.getPhotosInterface();

        //Sets up parameters to filter photos used
        SearchParameters searchParameters = new SearchParameters();
        //Shows only photos with license CC0, or Creative Commons. Redundant, but needed in case of group changes to allow copyright photos.
        searchParameters.setLicense("9");
        //Sets search parameter to 'Safe'. Redundant, but needed in case of group changes to allow adult content.
        searchParameters.setSafeSearch("1");
        //Public domain group - all submitted images are free to use and contain no adult content.
        searchParameters.setGroupId("2178061@N21");

        List<Photo> photoList = photosInterface.search(searchParameters,500,1);
        Map<Photo,Map<String,String>> finalPhotoMap = new HashMap<>();

        for(Photo photo : photoList)
        {
            boolean addPhoto = false;
            Collection<Exif> exif;
            try
            {
                exif = photosInterface.getExif(photo.getId(),flickrService.getSharedSecret());

            } catch (Exception e)
            {
                continue;
            }
            List<Exif> exifList = exif.stream().collect(Collectors.toList());

            for(Exif item : exifList)
            {
                if(item.getTag().equals("Aperture"))
                {
                    for(Exif item2 : exifList)
                    {
                        if(item2.getTag().equals("ISO Speed"))
                        {
                            for(Exif item3 : exifList)
                            {
                                if(item3.getTag().equals("Exposure"))
                                {
                                    addPhoto = true;
                                }
                            }
                        }
                    }
                }
            }

            if(addPhoto)
            {
                Map<String,String> exifDataList = new HashMap<>();

                for(Exif item : exifList)
                {
                    exifDataList.put(item.getLabel(),item.getRaw());
                }
                finalPhotoMap.put(photo,exifDataList);
            }
        }

        return ok(index.render(finalPhotoMap));
    }

}
