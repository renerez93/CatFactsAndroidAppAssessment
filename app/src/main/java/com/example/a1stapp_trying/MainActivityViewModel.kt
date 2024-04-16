package com.example.a1stapp_trying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    // These are the variables to be used in this App
    var contador = MutableLiveData(1)
    var contadorTotal = MutableLiveData(1)


    // Transient Data Storage
    data class TempItem(val id: Int, val content: String?)
    class TransientDataStorage {
        private val transientDataList = mutableListOf<TempItem>()
        /* Add Item */ fun addItem(item: TempItem) { transientDataList.add(item) }
        /* Get Item */ fun getItemById(id: Int): TempItem? { return transientDataList.find { it.id == id } }
    }

    private val transientDataStorage = TransientDataStorage()

    // These are the variables/processes for the API calls
    private val _TheCatFact = MutableLiveData("There is no Cat Fact")
    val TheCatFact: LiveData<String> get() = _TheCatFact

    init {
        viewModelScope.launch {
            getFact()
        }
    }

    // This function obtains a new CatFact
    private suspend fun getFact() {
        val catFact = RetrofitClient.catFactService.getCatFact().toString()
        _TheCatFact.value = catFact.dropLast(1).removeRange(0, 21)
        transientDataStorage.addItem(TempItem(contador.value ?: 0, catFact))
    }

    // This function obtains a old CatFact
    private suspend fun oldFact(a: Int) {
        val catFact = transientDataStorage.getItemById(a).toString()
        _TheCatFact.value = catFact.dropLast(2).removeRange(0, 44)
    }

    suspend fun newFact() {
        viewModelScope.launch {

            // This increments the COUNTERS shown in the main screen
            val a = contador.value
            val b = contadorTotal.value

            if (a != null) {
                if( a < b!!){
                    contador.value = contador.value?.plus(1)
                    oldFact(a+1)
                }else{
                    contador.value = contador.value?.plus(1)
                    contadorTotal.value = contadorTotal.value?.plus(1)
                    // This
                    getFact()
                }
            }
        }
    }

    suspend fun prevFact() {
        viewModelScope.launch {

            if( contador.value!! > 1 ){
                // This increments the COUNTERS shown in the main screen
                val FactNumber = contador.value

                contador.value = contador.value?.minus(1)
                oldFact(FactNumber!!-1)
            }else{
                contador.value = 1
            }
        }
    }

}