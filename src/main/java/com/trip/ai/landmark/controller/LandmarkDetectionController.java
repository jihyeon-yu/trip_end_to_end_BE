package com.trip.ai.landmark.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageContext;
import com.google.cloud.vision.v1.ImageContext.Builder;
import com.google.protobuf.ByteString;
import com.trip.ai.landmark.model.dto.Landmark;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=utf8")
public class LandmarkDetectionController {

    @PostMapping("/detectLandmark")
    public List<Landmark> detectLandmark(@RequestParam("file") MultipartFile file) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(file.getInputStream());

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LANDMARK_DETECTION).build();

        // Set language hints to Korean
        ImageContext context = ImageContext.newBuilder().addLanguageHints("ko").build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .setImageContext(context)
                .build();
        requests.add(request);

        List<Landmark> landmarks = new ArrayList<>();
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return null;
                }

                for (EntityAnnotation annotation : res.getLandmarkAnnotationsList()) {
                    Landmark landmark = new Landmark();
                    landmark.setDescription(annotation.getDescription());
                    if (annotation.getLocationsCount() > 0) {
                        landmark.setLatitude(annotation.getLocations(0).getLatLng().getLatitude());
                        landmark.setLongitude(annotation.getLocations(0).getLatLng().getLongitude());
                    }
                    landmarks.add(landmark);
                }
            }
        }
        return landmarks;
    }
}
