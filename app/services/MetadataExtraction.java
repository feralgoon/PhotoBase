package services;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetadataExtraction
{
    public Map<String,String> getMetadata(File photo)
    {
        Map<String,String> finalMetaData = new HashMap<>();
        try
        {
            Metadata metadata = ImageMetadataReader.readMetadata(photo);

            List<String> tagsWanted = new ArrayList<>();
            tagsWanted.add("Artist");
            tagsWanted.add("Exposure Time");
            tagsWanted.add("F-Number");
            tagsWanted.add("ISO Speed Ratings");
            tagsWanted.add("Date/Time Original");
            tagsWanted.add("Exposure Time");
            tagsWanted.add("Make");
            tagsWanted.add("Model");
            tagsWanted.add("Lens Model");

            for (Directory directory : metadata.getDirectories())
            {
                for(Tag tag : directory.getTags())
                {
                    if((directory.getName().equals("Exif SubIFD") || directory.getName().equals("Exif IFD0")) && tagsWanted.contains(tag.getTagName()))
                    {
                        finalMetaData.put(tag.getTagName(),tag.getDescription());
                    }
                }
            }
        } catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }

        return finalMetaData;
    }
}
