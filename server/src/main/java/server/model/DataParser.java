package server.model;

import static java.lang.Double.parseDouble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.groupingBy;

public class DataParser {
    public record Coordinate(double lat, double lon) {};
    public record COVIDData(String country, double confirmed, double deaths, double recovered, Coordinate coord) {}

    static Optional<String> fileNameResource(String filename) {
        try {
            URL resource = DataParser.class.getResource(filename);

            if (resource == null) {
                System.err.println("Failed to load resource: " + filename);
                return Optional.empty();
            };

            File file = new File(resource.toURI());

            if (!file.exists()) {
                System.err.println("File does not exist: " + filename);
                return Optional.empty();
            }

            return Optional.of(file.toString());
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    static Optional<COVIDData> makeCOVIDRecord(String[] data) {
        try {
            String country = data[3];
            var confirmed = parseDouble(data[7]);
            var deaths = parseDouble(data[8]);
            var recovered = parseDouble(data[9]);
            Coordinate coord = new Coordinate(parseDouble(data[5]), parseDouble(data[6]));
            return Optional.of(new COVIDData(country, confirmed, deaths, recovered, coord));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Map<String, List<COVIDData>>> readAllLines(String filename) {
        try (var reader = new BufferedReader(new FileReader(filename))) {
            return Optional.of(reader.lines()
                    .skip(1)
                    .map(line -> makeCOVIDRecord(line.split(",")))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(groupingBy(COVIDData::country)));
        } catch (IOException e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    public static Optional<Map<String, List<COVIDData>>> parseData(String filename) {
        return readAllLines(fileNameResource(filename).orElse(""));
    }
}
