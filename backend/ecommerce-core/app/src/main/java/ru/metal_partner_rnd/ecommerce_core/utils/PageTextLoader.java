package ru.metal_partner_rnd.ecommerce_core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import java.nio.file.Paths;
//import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
//import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.net.URL;

@Component
public class PageTextLoader {

    //@Value("${resources.template.dir:/templates/}")
    private String textDir = "static/texts/";

    /*
    loads test from file (specifically to include it as a page content)
    */
    public String[] loadPageText(String name) {
        String[] result = new String[] {"Unable to load the text", null};
        String stringPath = String.format("%s%s.txt", textDir, name);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(stringPath), StandardCharsets.UTF_8))) {
            //Path resourcePath = Paths.get(getClass().getResource(stringPath).toURI());
            // try (BufferedReader reader = Files.newBufferedReader(resourcePath, StandardCharsets.UTF_8)) {
            result[1] = reader.lines().collect(Collectors.joining("\n"));
            result[0] = null;
            // } catch (IOException ex) {
            //     result[0] = result[0] + ex.getMessage();
            // }
        } catch (FileSystemNotFoundException ex) {
            result[0] = result[0] + String.format(": No such text (%s) file (for page %s): %s, (%s)", stringPath, name, ex.getMessage(), getClass().getResource(stringPath).toURI().toString());
        } catch (SecurityException ex) {
            result[0] = result[0] + String.format(": The security manager doesn't allow to access a text file (for page %s): %s", name, ex.getMessage());
        } catch (IOException ex) {
            result[0] = result[0] + ": " + ex.getMessage() + " (path: " + stringPath + ")";
        } finally {
            return result;
        }
        
    }
}