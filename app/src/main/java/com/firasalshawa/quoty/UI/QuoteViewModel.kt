package com.firasalshawa.quoty.UI

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.firasalshawa.quoty.QuoteApplication
import com.firasalshawa.quoty.models.QuoteResponse
import com.firasalshawa.quoty.repository.QuoteRepository
import com.firasalshawa.quoty.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import okhttp3.Dispatcher
import okio.IOException
import retrofit2.Response

class QuoteViewModel(
    app:Application,
    val quoteRepository : QuoteRepository
) : AndroidViewModel(app) {


    val singleQuoteResponse : MutableLiveData<Resource<QuoteResponse>>
        = MutableLiveData()

    var offlineQuote : MutableLiveData<QuoteResponse> = MutableLiveData()

    val quotesLiveData : MutableLiveData<List<QuoteResponse>> = MutableLiveData()

    val likedQuotesLiveData : MutableLiveData<List<QuoteResponse>> = MutableLiveData()

    fun getRandomQuote() = viewModelScope.launch(Dispatchers.IO) {
        safeSingleQuote()
    }

    fun getAllLikedQuotes() = quoteRepository.getAllLikedQuotes()

    private suspend fun safeSingleQuote(){
        singleQuoteResponse.postValue(Resource.Loading())
        if(hasInternetConnection()){
            val response = quoteRepository.getQuote()
            singleQuoteResponse.postValue(handelSingleQuoteResponse(response))
            quoteRepository.insertQuote(response.body() as QuoteResponse)
        }else{
           val quote = getRandomQuoteDB()
            offlineQuote.postValue(quote)
        }
    }

    fun updateFav(quote: QuoteResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            quote.fav = !quote.fav
            quoteRepository.updateQuote(quote)
        }

    private fun getRandomQuoteDB() : QuoteResponse{
        return quoteRepository.getRandomQuoteDB()
    }


    suspend fun deleteQuote(quote: QuoteResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.deleteQuote(quote)
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

    fun isDeviceOnline():Boolean = hasInternetConnection()

}

