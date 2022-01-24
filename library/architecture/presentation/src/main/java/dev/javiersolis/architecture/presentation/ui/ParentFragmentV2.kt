package dev.javiersolis.architecture.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.javiersolis.architecture.presentation.viewmodel.ParentViewModel

abstract class ParentFragmentV2<T:ParentViewModel> : Fragment() {


    lateinit var vmParent : ParentViewModel
    val onBackPress = fun(){
        Toast.makeText(context,"no puede retroceder en estos momentos",Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            runOnlyNoWorking {
                onBackPress()
            }
        }
    }

    /*
     * llamar solo una ves, sino reinstancia la pantalla
     */
    abstract val viewBindingChild:()->ViewBinding

    abstract val viewModelChild:()->ParentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = FragmentDummyBinding.inflate(layoutInflater)
        val binding = this.viewBindingChild()

        this.vmParent = this.viewModelChild()

        configView()
        subscribe()
        events()
        return binding.root
        //return binding.root
    }

    open fun subscribe(){}
    open fun configView(){}
    open fun events() {}


    fun runOnlyNoWorking(f:()->Unit){
        if( vmParent.getIsWorking().value == true){
            Toast.makeText (context, "R.string.general_msg_is_working",Toast.LENGTH_LONG).show()
        }
        else{
            f()
        }
    }

}