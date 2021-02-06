package com.firasalshawa.quoty.UI

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.firasalshawa.quoty.QuoteApplication
import com.firasalshawa.quoty.models.QuoteResponse
import com.firasalshawa.quoty.repository.QuoteRepository
import com.firasalshawa.quoty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class QuoteViewModel(
    app:Application,
    val quoteRepository : QuoteRepository
) : AndroidViewModel(app) {


    val singleQuoteResponse : MutableLiveData<Resource<QuoteResponse>>
        = MutableLiveData()

    fun getRandomQuote() = viewModelScope.launch {
        safeSingleQuote()
    }

    private suspend fun safeSingleQuote(){
        singleQuoteResponse.postValue(Resource.Loading())
        if(hasInternetConnection()){
            val response = quoteRepository.getQuote()
            singleQuoteResponse.postValue(handelSingleQuoteResponse(response))
        }
    }

    private fun handelSingleQuoteResponse(response:Response<QuoteResponse>):Resource<QuoteResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<QuoteApplication>()
            .getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        // TODO: 2/5/2021 complete the network connectivity check function

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val  activeNetwork = connectivityManager.activeNetwork ?: return  false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{

                capabilities.hasTransport(TRANSPORT_WIFI)->  true
                capabilities.hasTransport(TRANSPORT_CELLULAR)->  true
                capabilities.hasTransport(TRANSPORT_ETHERNET)->  true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }

            return false
        }
    }
}

