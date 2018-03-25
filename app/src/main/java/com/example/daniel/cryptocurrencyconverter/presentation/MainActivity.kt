package com.example.daniel.cryptocurrencyconverter.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.presentation.main.MainController
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Route
import org.joda.money.CurrencyUnit


class MainActivity : AppCompatActivity() {

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list : List<String> = listOf("")
        CurrencyUnit.registerCurrency("BTC",-1,8,list)


      //  (applicationContext as BaseApplication).mApplicationComponent.inject(this)

//        activityComponent = DaggerActivityComponent.builder()
//                .mainActivityModule(MainActivityModule(this))
//                .build()
//        activityComponent.inject(this)



        router = Conductor.attachRouter(this, container_main as ViewGroup, savedInstanceState)

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val controller = getCurrentController()
        when(item!!.itemId){
            R.id.action_refresh -> (controller as MainController).onRefreshTapped()
        }
        return true
    }

    private fun getCurrentController():Controller{

        val backStack = router.backstack
        if (backStack.size == 0) {
            throw IllegalStateException("Attempting to retrieve controller from empty backstack")
        }
        return backStack[backStack.size - 1]
                .controller()
    }

}
