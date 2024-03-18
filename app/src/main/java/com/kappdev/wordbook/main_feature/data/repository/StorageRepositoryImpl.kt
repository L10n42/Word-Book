package com.kappdev.wordbook.main_feature.data.repository

import android.content.Context
import android.net.Uri
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