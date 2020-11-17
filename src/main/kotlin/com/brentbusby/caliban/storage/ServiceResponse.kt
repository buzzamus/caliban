package com.brentbusby.caliban.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream

data class ServiceResponse(val isSuccess: Boolean, val data: String? = null) {

    companion object {
        fun ok(data: String): ServiceResponse {
            return ServiceResponse(isSuccess = true, data = data)
        }

        fun fail(): ServiceResponse {
            return ServiceResponse(isSuccess = false)
        }
    }
}