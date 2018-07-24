package services;
/*
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.Exif;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.util.*;

public class FlickrAPICall
{
    private PhotosInterface photosInterface;
    private PeopleInterface peopleInterface;
    private Flickr flickr;
    private FlickrService flickrService;

    public FlickrAPICall()
    {
        this.flickrService = new FlickrService();
        this.flickr = flickrService.getFlickr();
        this.peopleInterface = flickr.getPeopleInterface();
        this.photosInterface = flickr.getPhotosInterface();
    }

    public Map<Photo,Map<String,String>> getPhotoMap() throws FlickrException
    {
        //Sets up parameters to filter photos used
        SearchParameters searchParameters = new SearchParameters();
        //Shows only photos with license CC0, or Creative Commons. Redundant, but needed in case of group changes to allow copyright photos.
        searchParameters.setLicense("9");
        //Sets search parameter to 'Safe'. Redundant, but needed in case of group changes to allow adult content.
        searchParameters.setSafeSearch("1");
        //Public domain group - all submitted images are free to use and contain no adult content.
        searchParameters.setGroupId("2178061@N21");

        List<Photo> photoList = photosInterface.search(searchParameters,250,1);
        Map<Photo,Map<String,String>> finalPhotoMap = new HashMap<>();
        Collection<Exif> exif;

        //exif = photoList.forEach(photo -> photosInterface.getExif(photo.getId(),flickrService.getSharedSecret()));

        long totalMS = 0;
        int x = 0;
        for(Photo photo : photoList)
        {
            long startTime = new Date().getTime();

            boolean addPhoto = false;
            long startTime1 = new Date().getTime();
            try
            {
                exif = photosInterface.getExif(photo.getId(),flickrService.getSharedSecret());
            } catch (Exception e)
            {
                System.out.println("Data not shared");
                continue;
            }
            long endTime1 = new Date().getTime();
            System.out.println((endTime1 - startTime1) + "ms");
            List<Exif> exifList = new ArrayList<>(exif);
            List<String> tags = new ArrayList<>();
            for(Exif item : exifList)
            {
                tags.add(item.getTag());
            }

            if (tags.contains("FocalLength") && tags.contains("ISO") && tags.contains("ExposureTime") && tags.contains("Lens") && tags.contains("Model"))
            {
                addPhoto = true;
            }


            //exifList = exifList.stream().filter(item -> item.getTag().equals("Aperture") && item.getTag().equals("ISO") && item.getTag().equals("ExposureTime")).collect(Collectors.toList());


            if(addPhoto)
            {
                Map<String,String> exifDataList = new HashMap<>();

                for(Exif item : exifList)
                {
                    exifDataList.put(item.getLabel(),item.getRaw());
                }
                finalPhotoMap.put(photo,exifDataList);
            }

            long endTime = new Date().getTime();
            System.out.println("Photo iteration " + x + " took " + (endTime - startTime) + " ms");
            totalMS += (endTime - startTime);
            x++;

        }
        System.out.println("Total time taken: " + totalMS + " ms");

        return finalPhotoMap;
    }

    public PeopleInterface getPeopleInterface()
    {
        return peopleInterface;
    }
}
*/