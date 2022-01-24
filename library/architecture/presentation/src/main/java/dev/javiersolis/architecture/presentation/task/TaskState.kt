package dev.javiersolis.architecture.presentation.task

import androidx.lifecycle.MutableLiveData
import dev.javiersolis.architecture.presentation.viewmodel.ParentViewModel

/**
 * Created by Javier J. Solis Flores on 28/12/21.
 * @email solis.unmsm@gmail.com
 * @github https://github.com/JavierSolis
 */
class TaskState(val vm:ParentViewModel,val state: MutableLiveData<Int>) {
    private var stateError = -1
    private var fEx:(ex:Exception)->Unit={}
    private var fAfter:()->Unit={}

    fun setError(stateError:Int):TaskState{
        this.stateError = stateError
        this.fEx = { state.postValue(this.stateError)}
        return this
    }

    fun setFunEx(fError:(ex:Exception)->Unit):TaskState{
        this.fEx = fError
        return this
    }
    fun setFunAfter(fAfter:()->Unit):TaskState{
        this.fAfter = fAfter
        return this
    }


    fun run(fProcess:()->Unit){
        vm.launchOneState(
            fEx=this.fEx,
            fAfter = this.fAfter
        ){
            fProcess()
        }
    }
}