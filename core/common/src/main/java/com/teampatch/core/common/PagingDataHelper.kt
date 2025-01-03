package com.teampatch.core.common

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class PagingDataHelper<T : Any>(originPagingDataFlow: Flow<PagingData<T>>) {

    private val itemInsertFlow = MutableStateFlow<List<T>>(emptyList())
    private val itemEditFlow = MutableStateFlow<HashSet<Pair<T, T>>>(hashSetOf())
    private val itemDeleteFlow = MutableStateFlow<HashSet<T>>(hashSetOf())

    val pagingDataInsertedItems: Flow<List<T>> = itemInsertFlow
        .combine(itemEditFlow) { pagingData, itemEdit ->
            if (itemEdit.isEmpty()) return@combine pagingData

            pagingData.map { value ->
                itemEdit.find { it.first == value }?.second ?: value
            }
        }
        .combine(itemDeleteFlow) { pagingData, itemDelete ->
            if (itemDelete.isEmpty()) return@combine pagingData

            pagingData.filter {
                !itemDelete.contains(it)
            }
        }

    val pagingDataFlow: Flow<PagingData<T>> = originPagingDataFlow
        .combine(itemEditFlow) { pagingData, itemEdit ->
            if (itemEdit.isEmpty()) return@combine pagingData

            pagingData.map { value ->
                itemEdit.find { it.first == value }?.second ?: value
            }
        }
        .combine(itemDeleteFlow) { pagingData, itemDelete ->
            if (itemDelete.isEmpty()) return@combine pagingData

            pagingData.filter {
                !itemDelete.contains(it)
            }
        }

    fun addItem(newItem: T, reversed: Boolean = false) = itemInsertFlow.update {
        it.toMutableList().apply {
            if (reversed) add(0, newItem) else add(newItem)
        }
    }

    fun editItem(oldItem: T, newItem: T) = itemEditFlow.update {
        it.toHashSet().apply {
            add(Pair(oldItem, newItem))
        }
    }

    fun deleteItem(item: T) = itemDeleteFlow.update {
        it.toHashSet().apply {
            add(item)
        }
    }
}