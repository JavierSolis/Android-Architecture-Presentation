package dev.javiersolis.architecture.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import dev.javiersolis.architecture.presentation.viewmodel.ParentViewModel
//TODO se podria poner como template la clase viewmodel para poder usarlo direcatmente sin tener dos metodos en el hijo para los viewmodel

abstract class ParentFragment : ParentOnlyFragment() {
    lateinit var vmParent : ParentViewModel

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
    abstract val viewModelChild:()->ParentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = FragmentDummyBinding.inflate(layoutInflater)
        val binding = this.viewBindingChild()
        this.vmParent = this.viewModelChild()

        firstConfig()
        configView()
        subscribe()
        events()
        lastConfig()
        return binding.root
        //return binding.root
    }

    open fun subscribe(){}


    fun runOnlyNoWorking(f:()->Unit){
        if( vmParent.getIsWorking().value == true){
            Toast.makeText (context, "R.string.general_msg_is_working",Toast.LENGTH_LONG).show()
        }
        else{
            f()
        }
    }

}