package com.tecsup.avisoscampus

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.datastore.preferences.preferencesDataStore
import com.tecsup.avisoscampus.data.AvisosRepositoryDataStore
import com.tecsup.avisoscampus.ui.PantallaAvisos
import com.tecsup.avisoscampus.ui.theme.AvisosCampusTheme
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel
import com.tecsup.avisoscampus.viewmodel.AvisosViewModelFactory

private val Context.dataStore by preferencesDataStore(name = "avisos_prefs")

class MainActivity : ComponentActivity() {

    private val vm: AvisosViewModel by viewModels {
        AvisosViewModelFactory(AvisosRepositoryDataStore(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        setContent {
            AvisosCampusTheme {
                PantallaAvisos(vm)
            }
        }
    }
}
