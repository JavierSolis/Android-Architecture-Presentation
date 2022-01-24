package dev.javiersolis.architecture.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class ParentOnlyFragment : Fragment() {
    val onBackPress = fun(){
        Toast.makeText(context,"no puede retroceder en estos momentos",Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPress()
        }
    }

    /*
     * llamar solo una ves, sino reinstancia la pantalla
     */
    abstract val viewBindingChild:()->ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = FragmentDummyBinding.inflate(layoutInflater)
        val binding = this.viewBindingChild()

        firstConfig()
        configView()
        events()
        lastConfig()
        return binding.root
        //return binding.root
    }

    open fun lastConfig(){}
    open fun firstConfig(){}
    open fun configView(){}
    open fun events() {}

}