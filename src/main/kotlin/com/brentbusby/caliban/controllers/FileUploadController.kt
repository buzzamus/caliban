package com.brentbusby.caliban.controllers

import com.brentbusby.caliban.storage.StorageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.io.BufferedReader
import java.io.IOException

@RestController
class FileUploadController {
    val logger = LoggerFactory.getLogger(FileUploadController::class.java)

    @PostMapping("/fileupload")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile): ServiceResponse {
        logger.info("uploading file - ", file.name)
        val content = file.inputStream.bufferedReader().use(BufferedReader::readText)
        logger.info("file content - ", content)
        return ServiceResponse.ok(content)
    }
}