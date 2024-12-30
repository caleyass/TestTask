package com.obrio.test.data.local.converter

import androidx.room.TypeConverter
import com.obrio.test.utils.TransactionCategory

class TransactionCategoryConverter {
    @TypeConverter
    fun fromCategory(category: TransactionCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(name: String): TransactionCategory {
        return TransactionCategory.valueOf(name)
    }
}