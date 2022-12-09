package ke.derrick.androidroomwithpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import ke.derrick.androidroomwithpaging.viewmodels.MainActivityViewModel
import ke.derrick.androidroomwithpaging.viewmodels.MainActivityViewModelFactory
import ke.derrick.androidroomwithpaging.RandomTextApplication
import ke.derrick.androidroomwithpaging.databinding.ActivityMainBinding
import ke.derrick.androidroomwithpaging.ui.adapters.RandomTextListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            (application as RandomTextApplication).database.randomTextDao())}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val randomTextAdapter = RandomTextListAdapter(this)
        val recyclerView = binding.recyclerView

        lifecycleScope.launch {
            viewModel.allRandomTexts.collectLatest {
                randomTextAdapter.submitData(it)
            }

        }
        recyclerView.adapter = randomTextAdapter
    }

}