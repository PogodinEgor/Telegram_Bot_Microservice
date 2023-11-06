package ru.relex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.entity.AppDocument;
import ru.relex.entity.AppPhoto;
import ru.relex.entity.BinaryContent;
import ru.relex.service.FileService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@RequiredArgsConstructor
@RequestMapping("/file")
@RestController
public class FileController {

    private final FileService fileService;

    @RequestMapping(method = RequestMethod.GET, value = "get-doc")
    public void getDoc(@RequestParam("id") String id, HttpServletResponse response){
        AppDocument doc = fileService.getDocument(id);
        if(doc == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType(MediaType.parseMediaType(doc.getMimeType()).toString());
        response.setHeader("Content-disposition","attavhment; filename=" + doc.getDocName());
        response.setStatus(HttpServletResponse.SC_OK);

        BinaryContent binaryContent = doc.getBinaryContent();
        try{
            ServletOutputStream out = response.getOutputStream();
            out.write(binaryContent.getFileAsArrayOfBytes());
            out.close();
        } catch (IOException e){
            log.error(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @RequestMapping(method = RequestMethod.GET, value = "get-photo")
    public void getPhoto(@RequestParam("id") String id,HttpServletResponse response){
        AppPhoto photo = fileService.getPhoto(id);
        if(photo == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType(MediaType.IMAGE_JPEG.toString());
        response.setHeader("Content-disposition","attavhment;");
        response.setStatus(HttpServletResponse.SC_OK);

        BinaryContent binaryContent = photo.getBinaryContent();
        try{
            ServletOutputStream out = response.getOutputStream();
            out.write(binaryContent.getFileAsArrayOfBytes());
            out.close();
        } catch (IOException e){
            log.error(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
