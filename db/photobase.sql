CREATE TABLE FileExtension (
                FileExtensionId INT AUTO_INCREMENT NOT NULL,
                FileExtensionName VARCHAR(4) NOT NULL,
                PRIMARY KEY (FileExtensionId)
);

CREATE TABLE ExposureTime (
                ExposureTimeId INT AUTO_INCREMENT NOT NULL,
                ExposureLength VARCHAR(10) NOT NULL,
                PRIMARY KEY (ExposureTimeId)
);


CREATE TABLE Aperture (
                ApertureId INT AUTO_INCREMENT NOT NULL,
                ApertureName VARCHAR(10) NOT NULL,
                PRIMARY KEY (ApertureId)
);


CREATE TABLE Iso (
                IsoId INT AUTO_INCREMENT NOT NULL,
                IsoSpeed VARCHAR(10) NOT NULL,
                PRIMARY KEY (IsoId)
);


CREATE TABLE Photographer (
                PhotographerId INT AUTO_INCREMENT NOT NULL,
                PhotographerName VARCHAR(50) NOT NULL,
                PRIMARY KEY (PhotographerId)
);


CREATE TABLE Copyright (
                CopyrightId INT AUTO_INCREMENT NOT NULL,
                License VARCHAR(50),
                CopyrightName VARCHAR(50),
                PRIMARY KEY (CopyrightId)
);


CREATE TABLE Manufacturer (
                ManufacturerId INT AUTO_INCREMENT NOT NULL,
                ManufacturerName VARCHAR(25) NOT NULL,
                PRIMARY KEY (ManufacturerId)
);


CREATE TABLE LensModel (
                LensModelId INT AUTO_INCREMENT NOT NULL,
                LensModelName VARCHAR(50) NOT NULL,
                PRIMARY KEY (LensModelId)
);


CREATE TABLE CameraModel (
                CameraModelId INT AUTO_INCREMENT NOT NULL,
                ManufacturerId INT NOT NULL,
                CameraModelName VARCHAR(50) NOT NULL,
                PRIMARY KEY (CameraModelId)
);


CREATE TABLE Photo (
                PhotoId INT AUTO_INCREMENT NOT NULL,
                CopyrightId INT NOT NULL,
                PhotographerId INT NOT NULL,
                LensModelId INT NOT NULL,
                CameraModelId INT NOT NULL,
                IsoId INT NOT NULL,
                ApertureId INT NOT NULL,
                ExposureTimeId INT NOT NULL,
                DateTaken DATE NOT NULL,
                TimeTaken TIME NOT NULL,
                PRIMARY KEY (PhotoId)
);

ALTER TABLE Photo ADD CONSTRAINT fileextension_photo_fk
FOREIGN KEY (FileExtensionId)
REFERENCES FileExtension (FileExtensionId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT exposuretime_photo_fk
FOREIGN KEY (ExposureTimeId)
REFERENCES ExposureTime (ExposureTimeId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT aperture_photo_fk
FOREIGN KEY (ApertureId)
REFERENCES Aperture (ApertureId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT iso_photo_fk
FOREIGN KEY (IsoId)
REFERENCES Iso (IsoId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT photographer_photo_fk
FOREIGN KEY (PhotographerId)
REFERENCES Photographer (PhotographerId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT copyright_photo_fk
FOREIGN KEY (CopyrightId)
REFERENCES Copyright (CopyrightId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE CameraModel ADD CONSTRAINT cameramanufacturer_cameramodel_fk
FOREIGN KEY (ManufacturerId)
REFERENCES Manufacturer (ManufacturerId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT lensmodel_photo_fk
FOREIGN KEY (LensModelId)
REFERENCES LensModel (LensModelId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Photo ADD CONSTRAINT cameramodel_photo_fk
FOREIGN KEY (CameraModelId)
REFERENCES CameraModel (CameraModelId)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
