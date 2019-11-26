package ie.dublinbuspal.android.view.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import ie.dublinbuspal.android.DublinBusApplication
import ie.dublinbuspal.android.R
import javax.inject.Inject

class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject lateinit var themeRepository: ThemeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as DublinBusApplication).applicationComponent.inject(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        bindListeners()
    }

    private fun bindListeners() {
        val preferredThemePreference = findPreference<ListPreference>(getString(R.string.preference_key_preferred_theme))
        preferredThemePreference?.setOnPreferenceChangeListener { _, newValue ->
            themeRepository.setTheme(newValue as String)
            return@setOnPreferenceChangeListener true
        }
    }
}
