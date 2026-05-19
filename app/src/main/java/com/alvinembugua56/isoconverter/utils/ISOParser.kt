package com.alvinembugua56.isoconverter.utils

import com.alvinembugua56.isoconverter.models.ISOMetadata
import timber.log.Timber
import java.io.File
import java.io.RandomAccessFile

class ISOParser {
    companion object {
        private const val ISO_SECTOR_SIZE = 2048
        private const val PRIMARY_VOLUME_DESCRIPTOR_SECTOR = 16
        private const val ISO9660_IDENTIFIER = "CD001"
    }

    /**
     * Validates if a file is a valid ISO 9660 format
     */
    fun validateISO(isoFile: File): Boolean {
        return try {
            if (!isoFile.exists() || !isoFile.canRead()) {
                Timber.e("ISO file does not exist or cannot be read: ${isoFile.absolutePath}")
                return false
            }

            RandomAccessFile(isoFile, "r").use { raf ->
                // Check file size (minimum 32KB for valid ISO)
                if (raf.length() < 32 * 1024) {
                    Timber.e("ISO file too small: ${raf.length()} bytes")
                    return false
                }

                // Read and validate Primary Volume Descriptor
                raf.seek((PRIMARY_VOLUME_DESCRIPTOR_SECTOR * ISO_SECTOR_SIZE).toLong())
                val descriptor = ByteArray(8)
                raf.read(descriptor)

                // Check descriptor type (should be 0x01 for PVD)
                if (descriptor[0] != 0x01.toByte()) {
                    Timber.e("Invalid PVD descriptor type: ${descriptor[0]}")
                    return false
                }

                // Check ISO identifier
                val identifier = ByteArray(5)
                raf.seek((PRIMARY_VOLUME_DESCRIPTOR_SECTOR * ISO_SECTOR_SIZE + 1).toLong())
                raf.read(identifier)

                val identifierStr = String(identifier)
                if (identifierStr != ISO9660_IDENTIFIER) {
                    Timber.e("Invalid ISO9660 identifier: $identifierStr")
                    return false
                }

                true
            }
        } catch (e: Exception) {
            Timber.e(e, "Error validating ISO file")
            false
        }
    }

    /**
     * Extracts metadata from ISO 9660 file
     */
    fun extractMetadata(isoFile: File): ISOMetadata? {
        return try {
            RandomAccessFile(isoFile, "r").use { raf ->
                // Read Primary Volume Descriptor
                raf.seek((PRIMARY_VOLUME_DESCRIPTOR_SECTOR * ISO_SECTOR_SIZE).toLong())
                val pvd = ByteArray(ISO_SECTOR_SIZE)
                raf.read(pvd)

                ISOMetadata(
                    volumeIdentifier = extractString(pvd, 40, 32),
                    publisherIdentifier = extractString(pvd, 318, 128),
                    dataPreparerIdentifier = extractString(pvd, 446, 128),
                    applicationIdentifier = extractString(pvd, 574, 128),
                    copyrightFileIdentifier = extractString(pvd, 702, 37),
                    abstractFileIdentifier = extractString(pvd, 739, 37),
                    bibliographicFileIdentifier = extractString(pvd, 776, 37),
                    creationDateTime = extractDateTime(pvd, 813),
                    modificationDateTime = extractDateTime(pvd, 830),
                    expirationDateTime = extractDateTime(pvd, 847),
                    effectiveDateTime = extractDateTime(pvd, 864),
                    fileStructureVersion = pvd[881].toInt(),
                    totalSize = raf.length()
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Error extracting ISO metadata")
            null
        }
    }

    private fun extractString(data: ByteArray, offset: Int, length: Int): String {
        return try {
            String(data, offset, length).trim()
        } catch (e: Exception) {
            ""
        }
    }

    private fun extractDateTime(data: ByteArray, offset: Int): String {
        return try {
            // ISO 9660 DateTime format: YYYYMMDDHHMMSSFF
            val year = String(data.sliceArray(offset..offset + 3))
            val month = String(data.sliceArray(offset + 4..offset + 5))
            val day = String(data.sliceArray(offset + 6..offset + 7))
            val hour = String(data.sliceArray(offset + 8..offset + 9))
            val minute = String(data.sliceArray(offset + 10..offset + 11))
            "$day/$month/$year $hour:$minute"
        } catch (e: Exception) {
            "Unknown"
        }
    }
}
