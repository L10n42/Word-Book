package com.kappdev.wordbook.main_feature.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.data.util.StoreImageException
import com.kappdev.wordbook.core.domain.util.Result
import com.kappdev.wordbook.main_feature.domain.repository.StorageRepository
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val context: Context
) : StorageRepository {

    override fun storeImage(uri: Uri): Result<String> {
        val destinationFile = File(getImagesDirectory(), generateFileName())
        var outputStream: FileOutputStream? = null
        var inputStream: InputStream? = null

        return try {
            outputStream = FileOutputStream(destinationFile)
            inputStream = context.contentResolver.openInputStream(uri)

            if (inputStream != null) {
                copyFile(inputStream, outputStream)
                Result.Success(destinationFile.absolutePath)
            } else {
                Result.Failure(StoreImageException(context.getString(R.string.open_image_error)))
            }
        } catch (e: Exception) {
            Result.Failure(StoreImageException(context.getString(R.string.store_image_error)))
        } catch (e: FileNotFoundException) {
            Result.Failure(StoreImageException(context.getString(R.string.image_not_found)))
        } finally {
            outputStream?.close()
            inputStream?.close()
        }
    }

    override suspend fun storeImage(url: String): Result<String> {
        val destinationFile = File(getImagesDirectory(), generateFileName())
        val result = downloadBitmapFromUrl(url)?.saveToFile(destinationFile)

        return when (result) {
            is Result.Success -> Result.Success(destinationFile.absolutePath)
            is Result.Failure -> result
            null -> Result.Failure(StoreImageException(context.getString(R.string.download_image_error)))
        }
    }

    private fun Bitmap.saveToFile(file: File): Result<Unit> {
        return try {
            FileOutputStream(file).use { out ->
                this.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(StoreImageException(context.getString(R.string.store_image_error)))
        } catch (e: FileNotFoundException) {
            Result.Failure(StoreImageException(context.getString(R.string.image_not_found)))
        }
    }

    private suspend fun downloadBitmapFromUrl(url: String): Bitmap? {
        return try {
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(url).build()
            val result = (loading.execute(request) as SuccessResult).drawable

            (result as BitmapDrawable).bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun copyFile(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(4 * 1024)
        var read: Int
        while (input.read(buffer).also { read = it } != -1) {
            output.write(buffer, 0, read)
        }
        output.flush()
    }

    private fun getImagesDirectory(): File {
        val directory = File(context.filesDir, "images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }

    private fun generateFileName() = "IMG_${System.currentTimeMillis()}.jpg"
}