package com.example.t2a.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.t2a.utils.ApiResult
import java.io.IOException

abstract class BaseRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun <T> safeApiCall(apiCall : suspend () -> T) : ApiResult<T>{
        return try {
            ApiResult.Success(apiCall())
        }catch (e: IOException){
            ApiResult.Error("Network Error: ${e.message}")
        }catch (e: HttpException){
            ApiResult.Error("HTTP Error: ${e.message}")
        }catch (e: Exception){
            ApiResult.Error("Unexpected Error: ${e.message}")
        }
    }
}