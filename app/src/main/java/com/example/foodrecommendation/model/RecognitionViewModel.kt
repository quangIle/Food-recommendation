package com.example.foodrecommendation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecognitionListViewModel : ViewModel() {

    // This is a LiveData field. Choosing this structure because the whole list tend to be updated
    // at once in ML and not individual elements. Updating this once for the entire list makes
    // sense.
    private val _recognitionList = MutableLiveData<List<Recognition>>()
    val recognitionList: LiveData<List<Recognition>> = _recognitionList

    fun updateData(recognitions: List<Recognition>){
        _recognitionList.postValue(recognitions)
    }

}
val labelToName: Map<String, String> = mapOf(
    "Nem_chua" to "Nem chua",
    "Banh_cuon" to "Bánh cuốn",
    "Banh_pia" to "Bánh pía",
    "Banh_chung" to "Bánh chưng",
    "Goi_cuon" to "Gỏi cuốn",
    "Banh_bot_loc" to "Bánh bột lọc",
    "Banh_mi" to "Bánh mì",
    "Xoi_xeo" to "Xôi xéo",
    "Banh_khot" to "Bánh khọt",
    "Chao_long" to "Cháo lòng",
    "Bun_dau_mam_tom" to "Bún đậu mắm tôm",
    "Com_tam" to "Cơm tấm",
    "Pho" to "Phở",
    "Bun_bo_Hue" to "Bún bò Huế"
)
/**
 * Simple Data object with two fields for the label and probability
 */
data class Recognition(val label:String, val confidence:Float) {
    val name = labelToName[label].toString()
    // For easy logging
    override fun toString():String{
        return "$label / $probabilityString"
    }

    // Output probability as a string to enable easy data binding
    val probabilityString = String.format("%.1f%%", confidence * 100.0f)

}