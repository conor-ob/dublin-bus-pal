package ie.dublinbuspal.android.view.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import ie.dublinbuspal.android.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
//        settings_toolbar.setNavigationOnClickListener {
//            onBackPressed() //TODO
//        }
        supportFragmentManager.commitNow {
            replace(R.id.settings_container, PreferencesFragment())
        }
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}