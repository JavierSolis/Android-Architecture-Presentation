package dev.javiersolis.architecture.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.javiersolis.architecture.presentation.extension.default
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


open class ParentViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object{
        private var vm:ParentViewModel?=null
        fun config(vm:ParentViewModel):ParentViewModel{
            this.vm = vm
            return vm
        }

    }

    //region observables
    var _messageError = MutableLiveData<String>().default("")
    private var _isWorking = MutableLiveData<Boolean>().default(false)

    open fun getIsWorking() = _isWorking as LiveData<Boolean>
    open fun getShowMessageError() =_messageError as LiveData<String>
    //endregion observables


    //region events
    protected val fExLaunchDefault:(ex:Exception)->Unit={_messageError.postValue(it.message)}
    open fun launch(
        isProcessing:MutableLiveData<Boolean> = MutableLiveData(),
        fEx:(ex:Exception)->Unit=fExLaunchDefault,
        f:()->Unit) {
        viewModelScope.launch(defaultDispatcher) {
            isProcessing.postValue(true)
            _isWorking.postValue(true)
            _messageError.postValue("")
            try {
                f()
                //val response = userRepository.login(user, password)
            }catch (ex:Exception){
                ex.printStackTrace()
                fEx(ex)
            }
            isProcessing.postValue(false)
            _isWorking.postValue(false)
        }
    }

    val fEx:(ex:Exception)->Unit = {_messageError.postValue(it.message)}

    open fun customLaunch(
        isWorking:MutableLiveData<Boolean>,
        messageError:MutableLiveData<String>,
        fEx:(ex:Exception)->Unit=this.fEx,
        f:()->Unit) {
        viewModelScope.launch(defaultDispatcher) {
            _isWorking.postValue(true)
            isWorking.postValue(true)
            messageError.postValue("")
            try{
                f()
                //val response = userRepository.login(user, password)
            }catch (ex:Exception){
                ex.printStackTrace()
                fEx(ex)
            }
            isWorking.postValue(false)
            _isWorking.postValue(false)

        }
    }

    /**
     * custom launch para pantallas que tiene un estado con enum
     */
    open fun launchOneState(
        fEx:(ex:Exception)->Unit={},
        fAfter:()->Unit={},
        fProcess:()->Unit) {
        viewModelScope.launch(defaultDispatcher) {
            try{
                _isWorking.postValue(true)
                _messageError.postValue("")

                fProcess()
            }catch (ex:Exception){
                _messageError.postValue(ex.message)
                ex.printStackTrace()
                fEx(ex)
            }
            _isWorking.postValue(false)
            fAfter()
        }
    }
    //endregion events

}