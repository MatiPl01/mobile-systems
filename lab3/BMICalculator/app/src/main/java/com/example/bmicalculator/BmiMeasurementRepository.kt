package com.example.bmicalculator;

import kotlinx.coroutines.flow.Flow

class BmiMeasurementRepository(private val dao: BmiMeasurementDao) {
    fun getAll(): Flow<List<BmiMeasurement>> = dao.getAll()
    suspend fun save(rec: BmiMeasurement) = dao.insert(rec)
    suspend fun clear() = dao.clear()
    suspend fun delete(rec: BmiMeasurement) = dao.delete(rec)
}
